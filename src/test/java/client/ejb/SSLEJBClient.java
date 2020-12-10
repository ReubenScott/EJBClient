package client.ejb;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;

import com.kindustry.ejb.service.IManualTx;
import com.kindustry.jpa.model.Gurupu;

public class SSLEJBClient {

  private Properties env = new Properties();

  public SSLEJBClient() {
    // JBoss EAP 7.1.0.GA InitialContext
    env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
    env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
    env.put("jboss.naming.client.ejb.context", "true");
    env.put("jboss.naming.client.connect.timeout", "60000");
    env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
    env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
    env.put(Context.PROVIDER_URL, "http-remoting://192.168.211.128:8080");
    env.put(Context.SECURITY_PRINCIPAL, "ejb");  // ApplicationRealm 用戶 add-user.bat / application-users
    env.put(Context.SECURITY_CREDENTIALS, "123"); // 明文密碼

  }

  public void callJBossEJB() {
    try {
      Context context = new InitialContext(this.env);

      String fullEJBName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
      IManualTx convert = (IManualTx) context.lookup(fullEJBName);

      List<Gurupu> gurupus = convert.listGurupu(1);

      for (Gurupu item : gurupus) {
        System.out.println(item.getGurupucd());
        System.out.println(item.getGurupumei());
      }
      System.out.println(gurupus.size());
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  // @Test
  public void callSSLEJB() {
    // define EJB client properties
    final Properties props = new Properties();
    // define SSL encryption
    props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "true");
    props.put("remote.connection.default.connect.options.org.xnio.Options.SSL_STARTTLS", "true");
    // connection properties
    // props.put("remote.connections", "default");
    // props.put("remote.connection.default.host", "192.168.211.128");
    // props.put("remote.connection.default.port", "8080");
    // user credentials
    // props.put("remote.connection.default.username", "ejb");
    // props.put("remote.connection.default.password", "123");

    // props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
    // props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
    // props.put("remote.connection.default.connect.options.org.jboss.remoting3.RemotingOptions.HEARTBEAT_INTERVAL", "600000");

    // create EJB client configuration
    final EJBClientConfiguration clientConfiguration = new PropertiesBasedEJBClientConfiguration(props);

    // create and set a context selector
    final ContextSelector<EJBClientContext> contextSelector = new ConfigBasedEJBClientContextSelector(clientConfiguration);
    EJBClientContext.setSelector(contextSelector);

    InitialContext initialContext;
    try {
      initialContext = new InitialContext(env);

      // lookup SLSB
      // java:global/EJB-HelloWorld/HelloWorldBean!ejb.HelloWorld
      String fullEJBName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
      IManualTx convert = (IManualTx) initialContext.lookup(fullEJBName);
      // Call any of the Remote methods below to access the EJB
      List<Gurupu> gurupus = convert.listGurupu(1);

      for (Gurupu item : gurupus) {
        System.out.println(item.getGurupucd());
        System.out.println(item.getGurupumei());
      }
      // Assert.assertEquals("Hello World!", greeter.greet("World"));
    } catch (NamingException e) {
      e.printStackTrace();
    }

  }

  public static void main(String[] args) {
    SSLEJBClient sslejbclient = new SSLEJBClient();
    sslejbclient.callJBossEJB();
    // sslejbclient.callSSLEJB();
  }
}

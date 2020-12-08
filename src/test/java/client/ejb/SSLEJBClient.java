package client.ejb;

import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.naming.ejb.ejbURLContextFactory;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
import org.junit.Test;

import com.kindustry.ejb.service.IManualTx;
import com.kindustry.jpa.model.Gurupu;

public class SSLEJBClient {

  @Test
  public void test() {
    // define EJB client properties
    final Properties props = new Properties();
    // define SSL encryption
    props.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "true");
    props.put("remote.connection.default.connect.options.org.xnio.Options.SSL_STARTTLS", "true");
    // connection properties
    props.put("remote.connections", "default");
    props.put("remote.connection.default.host", "localhost");
    props.put("remote.connection.default.port", "4447");
    // user credentials
    props.put("remote.connection.default.username", "test");
    props.put("remote.connection.default.password", "1234");

    props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
    props.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
    props.put("remote.connection.default.connect.options.org.jboss.remoting3.RemotingOptions.HEARTBEAT_INTERVAL", "600000");

    // create EJB client configuration
    final EJBClientConfiguration clientConfiguration = new PropertiesBasedEJBClientConfiguration(props);

    // create and set a context selector
    final ContextSelector<EJBClientContext> contextSelector = new ConfigBasedEJBClientContextSelector(clientConfiguration);
    EJBClientContext.setSelector(contextSelector);

    // create InitialContext
    final Hashtable<Object, Object> contextProperties = new Hashtable<>();
    ejbURLContextFactory.class.getName();
    contextProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

    InitialContext initialContext;
    try {
      initialContext = new InitialContext(contextProperties);

      // lookup SLSB
      // java:global/EJB-HelloWorld/HelloWorldBean!ejb.HelloWorld
      String fullEJBName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
      IManualTx convert = (IManualTx) initialContext.lookup(fullEJBName);
      // Call any of the Remote methods below to access the EJB
      List<Gurupu> gurupus = convert.listGurupu(1);

      // Assert.assertEquals("Hello World!", greeter.greet("World"));
    } catch (NamingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}

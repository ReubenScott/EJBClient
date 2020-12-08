package client.ejb;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.kindustry.ejb.service.IManualTx;
import com.kindustry.jpa.model.Gurupu;

public class HelloClient {

  public static void main(String[] args) {

    final Properties env = new Properties();
    env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
    env.put(Context.PROVIDER_URL, "http-remoting://192.168.211.128:8080");
    env.put("jboss.naming.client.ejb.context", "true");
    env.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
    env.put(Context.SECURITY_PRINCIPAL, "ejb");
    env.put(Context.SECURITY_CREDENTIALS, "MTIz");
    try {
      Context context = new InitialContext(env);

      String fullEJBName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
      IManualTx convert = (IManualTx) context.lookup(fullEJBName);

      List<Gurupu> gurupus = convert.listGurupu(1);
      // System.out.println(h1.sayHello());
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }
}

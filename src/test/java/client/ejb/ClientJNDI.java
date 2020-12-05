package client.ejb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.kindustry.ejb.service.ContainerService;
import com.kindustry.ejb.service.IManualTx;
import com.kindustry.ejb.service.JdbcService;
import com.kindustry.jpa.model.Cdsyu;
import com.kindustry.jpa.model.Gurupu;
import com.kindustry.jpa.model.Nyukin;

public class ClientJNDI {

  BufferedReader brConsoleReader = null;
  Properties props;

  /**
   * 
   */
  InitialContext ctx;

  {
    InputStream in = ClientJNDI.class.getClassLoader().getResourceAsStream("jndi.properties");

    props = new Properties();
    try {
      props.load(in);
      ctx = new InitialContext(props);
    } catch (IOException | NamingException e) {
      e.printStackTrace();
    }
    brConsoleReader = new BufferedReader(new InputStreamReader(System.in));
  }

  public boolean listGurupu() {
    boolean flag = false;
    try {
      /* 设置属性信息 */
      Properties props = new Properties();
      // props.put(Context.INITIAL_CONTEXT_FACTORY,
      // "org.jboss.naming.remote.client.InitialContextFactory");
      // props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
      // props.put("jboss.naming.client.ejb.context", true);

      System.out.println("Looking up HelloWorld");
      // InitialContext().lookup("java:comp/env/ejb/HelloWorld");
      // JNDI路径 查看控制台输出文件 @remote 注解的

      // java:global/EJB-HelloWorld/HelloWorldBean!ejb.HelloWorld
      String fullEJBName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
      IManualTx convert = (IManualTx) ctx.lookup(fullEJBName);
      // Call any of the Remote methods below to access the EJB

      List<Gurupu> gurupus = convert.listGurupu(1);
      // List<Gurupu> gurupus = convert.findGurupuByMei("'グループ２'  or 1=1 ");

      for (Gurupu item : gurupus) {
        System.out.println(item.getGurupucd());
        System.out.println(item.getGurupumei());
      }

      System.out.println("Please view the console to see the output emitted by the EJB Method");
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  public boolean insertGurupu() {
    boolean flag = false;
    try {
      /* 设置属性信息 */
      Properties props = new Properties();
      // props.put(Context.INITIAL_CONTEXT_FACTORY,
      // "org.jboss.naming.remote.client.InitialContextFactory");
      // props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
      // props.put("jboss.naming.client.ejb.context", true);

      System.out.println("Looking up HelloWorld");
      // InitialContext().lookup("java:comp/env/ejb/HelloWorld");
      // JNDI路径 查看控制台输出文件 @remote 注解的

      // java:global/EJB-HelloWorld/HelloWorldBean!ejb.HelloWorld
      String fullEJBName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
      IManualTx convert = (IManualTx) ctx.lookup(fullEJBName);
      // Call any of the Remote methods below to access the EJB
      Gurupu gurupu = new Gurupu();
      for (int i = 10; i < 20; i++) {
        gurupu.setGurupucd(i);
        gurupu.setGurupumei("setGurupumei" + i);
        convert.addGurupu(gurupu);
      }

      /*
       * List<Gurupu> gurupus = convert.listGurupu(1); for (Gurupu item :
       * gurupus) { System.out.println(item.getGurupucd());
       * System.out.println(item.getGurupumei()); }
       */
      System.out.println("Please view the console to see the output emitted by the EJB Method");
    } catch (Throwable ex) {
      ex.printStackTrace();
    }
    return flag;
  }

  public void delGurupu() {
    String ejbName = "EJBServer/ConvertBean!com.kindustry.ejb.service.IManualTx";
    IManualTx convert;
    try {
      convert = (IManualTx) this.ctx.lookup(ejbName);
      // Call any of the Remote methods below to access the EJB
      convert.delGurupuByKey(3);
      convert.delGurupuByCondition(3);
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  public void findCdsyu() {
    String ejbName = "EJBServer/ContainerServiceBean!com.kindustry.ejb.service.ContainerService";
    ContainerService cdsyuService;
    try {
      cdsyuService = (ContainerService) this.ctx.lookup(ejbName);
      // Call any of the Remote methods below to access the EJB
      // List<Cdsyu> cdsyus = cdsyuService.queryCdsyuBysetumei("印刷");
      List<Cdsyu> cdsyus = cdsyuService.queryCdsyu("印刷方向  or 1=1 ");
      for (Cdsyu item : cdsyus) {
        System.out.println(item.getCdsyu());
        System.out.println(item.getCdsyusetumei());
        System.out.println(item.getStartdate());
        System.out.println(item.getEnddate());
      }
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  public void findNyukin() {
    String ejbName = "EJBServer/JdbcServiceBean!com.kindustry.ejb.service.JdbcService";

    JdbcService nyukinService;
    try {
      nyukinService = (JdbcService) this.ctx.lookup(ejbName);

      Calendar calendar = Calendar.getInstance();
      calendar.clear();
      calendar.set(2020, 4, 14, 10, 45);
      int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

      Timestamp nyukintime = new Timestamp(calendar.getTimeInMillis());
      System.out.println(nyukintime);
      System.out.println(day);

      // Call any of the Remote methods below to access the EJB
      // List<Nyukin> nyukins =
      // nyukinService.queryNyukin("2020-05-14 10:45:00");
      List<Nyukin> nyukins = nyukinService.queryNyukin(nyukintime);

      for (Nyukin item : nyukins) {
        System.out.println(item.getKanjano());
        System.out.println(item.getHkkumi());
        System.out.println(item.getSinryodate());
        System.out.println(item.getRaiinkaisu());
        System.out.println(item.getNyukindate());
        System.out.println(item.getHutankin());
        System.out.println(item.getJihikin());
        System.out.println(item.getNyukingaku());
        System.out.println(item.getTyoseikin());
        System.out.println(item.getJibaiseikyukbn());
        System.out.println(item.getTeiseinyukinkbn());
        System.out.println(item.getOpkbn());
        System.out.println(item.getNyukincomm());
        System.out.println(item.getKousintime());
        System.out.println(item.getRosaiseikyukbn());
        System.out.println(item.getNyukintime());
      }
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    ClientJNDI jndiClient = new ClientJNDI();
    // jndiClient.findNyukin();
    // jndiClient.findCdsyu();
    jndiClient.listGurupu();
    // jndiClient.delGurupu();
    // System.out.println(jndiClient.insertGurupu());

  }
}

package domain;


import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * Created by KURZRO on 07.04.2017.
 */
public class ContainerTest {

   private final LocalDateTime dateTime = LocalDateTime.now();
   private final String ip = "127.0.0.1";

   @Test
    public void doesDifferentIdFailEqualCheck(){
       Container containerOne = new Container("1", dateTime, ip, 1);
       Container containerTwo = new Container("2", dateTime, ip, 1);
      Assert.assertEquals(false, containerOne.equals(containerTwo));
   }

   @Test
   public void doesSameIdSucceedEqualCheck(){
      Container containerOne = new Container("1", dateTime, ip, 1);
      Container containerTwo = new Container("1", dateTime, ip, 1);
      Assert.assertEquals(true, containerOne.equals(containerTwo));
   }


}

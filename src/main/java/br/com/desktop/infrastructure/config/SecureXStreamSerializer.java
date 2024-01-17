package br.com.desktop.infrastructure.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.xml.XStreamSerializer;

import java.util.HashMap;
import java.util.Map;

public class SecureXStreamSerializer {
    //TODO: Parametrizar para não ficar em codigo
    private static final Map<String, Class<?>> legacyAlias = new HashMap<>(){
//        {
//            put("br.com.desktop.payment.bankslip.entities.VindiBankSlipPaymentDao", VindiBankSlipPaymentDao.class);
//        }
    };

    public static XStream xStream()  {
        XStream xStream = new XStream();
        xStream.setClassLoader(SecureXStreamSerializer.class.getClassLoader());
        xStream.allowTypesByWildcard(new String[] {"org.axonframework.**", "**"});
        for (Map.Entry<String, Class<?>> set : legacyAlias.entrySet()) {
            xStream.alias(set.getKey(), set.getValue());
        }
        return XStreamSerializer.builder().xStream(xStream).build().getXStream();
    }
}

package ga.tianyuge.test;

import ga.tianyuge.utils.IOUtil;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.junit.Test;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/08/30 17:02
 */
public class StringParsingTest {
    @Test
    public void test1() {
        String s = "" +
                "{\"header\":{\"applicationCode\":\"PRIVATE_CLOUD_SHSH_PL_DEV\",\"applicationGroupCode\":\"PUBLIC_CLOUD\",\"batchCount\":\"\",\"batchNum\":\"20211013134420\",\"externalSystemCode\":\"SAP\",\"interfaceCode\":\"SINV_RCV_TRX_IMP\",\"userName\":\"TECH\"},\"body\":[{\"esTrxNum\":\"5000051209\",\"trxYear\":\"2022\",\"esOuCode\":\"5300\",\"trxDate\":\"20220729\",\"esSupplierNum\":\"0040007971\",\"erpCreationDate\":\"20220729\",\"erpLastUpdateDate\":\"00000000\",\"remark\":\"\",\"sourceCode\":\"SAP\",\"rcvTrxLineList\":[{\"esTrxLineNum\":\"0001\",\"trxDate\":\"20220729\",\"trxYear\":\"2022\",\"esTrxTypeCode\":\"101\",\"quantity\":\"9300.000\",\"stockType\":\"\",\"currencyCode\":\"RMB\",\"esTaxCode\":\"\",\"esItemCode\":\"16999980899184\",\"esCategoryCode\":\"169999\",\"esOuCode\":\"5300\",\"esSupplierNum\":\"0040007971\",\"esFromPoNum\":\"4500109216\",\"esFromPoLineNum\":\"00010\",\"erpParentTrxNum\":\"\",\"erpParentTrxLineNum\":\"0000\",\"ERPPARENTtrxYear\":\"0000\",\"attribute_varchar38\":\"\",\"attribute_varchar39\":\"0000\",\"attribute_varchar40\":\"0000\",\"remark\":\"\"}]}]}" +
                "";
        System.out.println(s);
    }

    @Test
    public void test2() {
        String s = "{\"BODY\":\"[{\\\"EBELP\\\":\\\"00010\\\",\\\"KNTTP\\\":\\\"\\\",\\\"MATNR\\\":\\\"000029219981364607\\\",\\\"MENGE\\\":\\\"100.000000\\\",\\\"MEINS\\\":\\\"\\\",\\\"NETPR\\\":\\\"100.0000000000\\\",\\\"PEINH\\\":\\\"1.0000000000\\\",\\\"MATKL\\\":\\\"\\\",\\\"WERKS\\\":\\\"5202\\\",\\\"BANFN\\\":\\\"\\\",\\\"BNFPO\\\":\\\"\\\",\\\"MWSKZ\\\":\\\"JD\\\",\\\"AFNAM\\\":\\\"\\\",\\\"UNTTO\\\":\\\"\\\",\\\"UEBTO\\\":\\\"\\\",\\\"TXZ01\\\":\\\"\\\",\\\"TDLINE\\\":\\\"\\\",\\\"BEDNR\\\":\\\"\\\",\\\"UEBTK\\\":\\\"\\\",\\\"LEWED\\\":0},{\\\"EBELP\\\":\\\"00020\\\",\\\"KNTTP\\\":\\\"\\\",\\\"MATNR\\\":\\\"000047179981364586\\\",\\\"MENGE\\\":\\\"100.000000\\\",\\\"MEINS\\\":\\\"\\\",\\\"NETPR\\\":\\\"100.0000000000\\\",\\\"PEINH\\\":\\\"1.0000000000\\\",\\\"MATKL\\\":\\\"\\\",\\\"WERKS\\\":\\\"5201\\\",\\\"BANFN\\\":\\\"\\\",\\\"BNFPO\\\":\\\"\\\",\\\"MWSKZ\\\":\\\"JD\\\",\\\"AFNAM\\\":\\\"\\\",\\\"UNTTO\\\":\\\"\\\",\\\"UEBTO\\\":\\\"\\\",\\\"TXZ01\\\":\\\"\\\",\\\"TDLINE\\\":\\\"\\\",\\\"BEDNR\\\":\\\"\\\",\\\"UEBTK\\\":\\\"\\\",\\\"LEWED\\\":0},{\\\"EBELP\\\":\\\"00030\\\",\\\"KNTTP\\\":\\\"\\\",\\\"MATNR\\\":\\\"000029219981364633\\\",\\\"MENGE\\\":\\\"100.000000\\\",\\\"MEINS\\\":\\\"\\\",\\\"NETPR\\\":\\\"100.0000000000\\\",\\\"PEINH\\\":\\\"1.0000000000\\\",\\\"MATKL\\\":\\\"\\\",\\\"WERKS\\\":\\\"5201\\\",\\\"BANFN\\\":\\\"\\\",\\\"BNFPO\\\":\\\"\\\",\\\"MWSKZ\\\":\\\"JD\\\",\\\"AFNAM\\\":\\\"\\\",\\\"UNTTO\\\":\\\"\\\",\\\"UEBTO\\\":\\\"\\\",\\\"TXZ01\\\":\\\"\\\",\\\"TDLINE\\\":\\\"\\\",\\\"BEDNR\\\":\\\"\\\",\\\"UEBTK\\\":\\\"\\\",\\\"LEWED\\\":0}]\", \"BODY_Q\":\"\", \"HEADER\":\"{\\\"STATUS\\\":\\\"1\\\",\\\"BSART\\\":\\\"SH02\\\",\\\"LIFNR\\\":\\\"0040008206\\\",\\\"EKORG\\\":\\\"SHM1\\\",\\\"EKGRP\\\":\\\"PS1\\\",\\\"BUKRS\\\":\\\"5200\\\",\\\"ZCMISNO\\\":\\\"CON52002022014606\\\",\\\"DELIVERY_DATE\\\":\\\"\\\",\\\"ZCRTNAME\\\":\\\"外币合同\\\",\\\"ZSWZXR\\\":\\\"盛虹石化集团有限公司\\\",\\\"LONGTEXT\\\":\\\"\\\",\\\"ERNAM\\\":\\\"PI_USER\\\",\\\"WAERS\\\":\\\"USD\\\",\\\"EBELNESSC\\\":\\\"PO52002022004048\\\",\\\"UNSEZ\\\":\\\"\\\"}\"}";
        s = s.replaceAll("\"", "\"");
        s = s.replaceAll("\\\"", "\"");
//        s = s.replaceAll("\\\"", "\"");
        s = s.replaceAll("\\\\", "");
        System.out.println(s);
    }
}

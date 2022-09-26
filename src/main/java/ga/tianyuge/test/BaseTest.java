package ga.tianyuge.test;

import org.junit.Test;

import java.util.Base64;

/**
 * description
 *
 * @author guoqing.chen01@hand-china.com 2022/08/15 11:07
 */
public class BaseTest {

    @Test
    public void test1() {
        String encodedString = "eNqdVG9P00Ac/iqkr4XcdQPXvQPmCxMZRImJMb44upupdG3TdsaFkGCQsTGQGRAYwxBQVBIE/4COQfTL9Lr2FV/Bu67tWtQ3Nl3SPc/97vfnee5mOKRpsiQixeTSnNuokK+H3A1uqph7jM3JkoYpCmJAFhUY6O6/6BxvkncbHmkYkoINww+4NTF2D4AkZUS1oCGlNKrmGD4IAOiB/kb2l12nttppNp3GeWf3G1nesNofrNa22yyT5r69U3UbdbJwRFZbLFTHyMQZ+qORPOD5fpDq55N9EKb5oXQSDnj7F3UdK2KQ9e7YCAVzWEO6WcCKeTvnlQJBsltOj/ErIq/LZGnPnf9ISfxMk5GkMPT8lLTfd3YPnJMyrdB+M29dvO3Uy87pOV0nGRksYxPn/Hk9UaeyxcIU1v1ccFBg85DpmDKT43ckg4774QyHRFEthomt1pJ99oMsVMnKS6u1RiqHVrtGS7Grc1eXFbtx7M5tk3rFumxcXVbpbqjAgmngEO1kAIRPqFcGa3+022P+0q6INMlEciA8gHHMj+hcrDr1A3v9hCy2PT0VU0eimVUpNzqeZTIzbQCESQFGBGF09kEECPput+3Pr0jruUcZplrA+ghSpgOH1Jq0Rnftu7O37I+oUqbfZGXTOfhFy6Df1yNZKZCHQkLgkwIPQQokIkv+c95huOoNhw4U8gkgeB6SpadYL/mDg9eg0OqL9tFP5qqCJqslHJ6l4NiFuBq3Tb6o5CaKuqYaviwADAF4M84ESXZWaAtdVzK2pyUNimCBlsd7XRN31pkBJOaW4cz9UEP2pCgxPT0cuI25SEMldmJiJ1Ggbx8Aae8diKzyS+DjSO8isS62KFVUJFWZQKXw3CRAihdo+gSErIJSfvhffp99RHeWkRK5sIK/QRbPPeTTVlcAA5umjCO1ebdVHA0GtH5GdWNsXsz7mxum3zc3+xuYywyi";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);
    }
}

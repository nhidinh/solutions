package utilities.helper;

import org.apache.commons.codec.binary.Base64;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;


/**
 * @param
 * @author Nhi Dinh
 * @return
 * @since 1/18/2019
 */


public class StringEncrypt {
    private StringEncrypt(){

    }

    /********************************************************************
     *
     * encryptXOR(String message, String key)
     *
     * 		Uses provided key to encrypt provided message using simple XOR
     *
     *********************************************************************/

    public static String encryptXOR(String message, String key){

        try {
            if (message==null || key==null ) return null;

            char[] keys=key.toCharArray();
            char[] mesg=message.toCharArray();

            int ml=mesg.length;
            int kl=keys.length;
            char[] newmsg=new char[ml];

            for (int i=0; i<ml; i++){
                newmsg[i]=(char)(mesg[i]^keys[i%kl]);
            }
            mesg=null;
            keys=null;
            byte[] bytes = new byte[(int) (new String(newmsg).length())];

            return new String(Base64.encodeBase64(bytes));
//            return new String(new BASE64Encoder().encodeBuffer(new String(newmsg).getBytes()));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /********************************************************************
     *
     * decryptXOR(String message, String key)
     *
     * 		Uses provided key to decrypt provided message encrypted using the same key
     *
     *********************************************************************/

    public static String decryptXOR(String message, String key){
        try {
            if (message==null || key==null ) return null;

//            BASE64Decoder decoder = new BASE64Decoder();
            char[] keys=key.toCharArray();
//            char[] mesg=new String(decoder.decodeBuffer(message)).toCharArray();
            char[] mesg=new String(Base64.decodeBase64(message)).toCharArray();
            int ml=mesg.length;
            int kl=keys.length;
            char[] newmsg=new char[ml];

            for (int i=0; i<ml; i++){
                newmsg[i]=(char)(mesg[i]^keys[i%kl]);
            }
            mesg=null; keys=null;
            return new String(newmsg);
        }
        catch ( Exception e ) {
            return null;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.com.helpers;

/**
 *
 * @author IETECHADMIN
 */
public class TestProp {
    public String reqTransID;
    public String accountNo;
    public Integer amount;
    public String bankCode;
    public Integer channel;
    public String tranCode;
    public String narration;
    public String atm_Location;
    public String issuerBIN;
    public String pan;
    public String issuerfiid;
    public String acqfiid;
    public String stan;
}
//URL url = new URL("http://62.173.32.98/eChannel/api/eChannelPro/balanceEnquiry?channelid=1&AccountNo=00110100005&BankCode=10");
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//		conn.setRequestMethod("GET");
//		conn.setRequestProperty("Content-Type", "application/json");
//                conn.setRequestProperty("Authorization", "eCHANNEL dXNlcm5hbWU6JENXR1NlbmRNTkkkVjEkMTAwMDAkT0p1dFAzdDVBTDBiTjZQVU9WbHFOY3dVOUFNY1l1V2g1WlliMUVPL1VvVmNVR052");
//
//		if (conn.getResponseCode() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ conn.getResponseCode());
//		}
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//			(conn.getInputStream())));
//
//		String output;
//		System.out.println("Output from Server .... \n");
//		while ((output = br.readLine()) != null) {
//			System.out.println(output);
//		}
//
//		conn.disconnect();
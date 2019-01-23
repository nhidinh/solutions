package utilities.helper.jira;

import okhttp3.*;
import utilities.configuration.InitialData;
import utilities.helper.StringEncrypt;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Huong Trinh
 * @since 1/22/2019
 * @scope: create issue and import attachment to JiRA
 */
public class JiraHelper {

    private static String username = "xuanhuongtt2010";
    private static String encodedPassword = "W1tBVV9f";
    private static String keyPassword = "jira";
    private static String hostIp = "http://localhost:";
    private static String port = "8080";
    private static String issueLink = hostIp + port +"/rest/api/2/issue";
    private static String attachmentLink = hostIp + port + "/rest/api/2/issue/";
    private static String jenkinLink = "http://localhost:8080/job/ExtentReport_GIT/";
    private static String reportLink = InitialData.REPORT_FILE_PATH;
    private static String password = StringEncrypt.decryptXOR(encodedPassword, keyPassword);

    public static String createJiraIssue(List<String> failIDs){

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        // Add you JIRA Project Name, As Joseph suggested, title will be as below
        String jiraProjectName = "SOL";
        String title = "Portal Enrollment Search Defect";
        //Get fail test cases, remove the first empty ele
        String failTestIDs = failIDs.toString().replaceAll("[\\]\\[,]","");

        // Credentials to log in
        String cred = Credentials.basic(username, password);
        //Create CLIENT
        OkHttpClient okHttpClient = new OkHttpClient();

        //create body of request
        String jsonInput = "{\"fields\":" +
                "{\"project\":" +
                "{\"key\":\"" + jiraProjectName + "\"}," +
                "\"summary\":\"" + title + "\"," +
                "\"description\":\"List of fail testcase: \\n " + failTestIDs + "\\n" + jenkinLink + "\"," +
                "\"issuetype\":{\"name\":\"Bug\"}}}";

        RequestBody jsonBody = RequestBody.create(JSON, jsonInput);
        //make request
        Request request = new Request.Builder()
                .url(issueLink)
                .post(jsonBody)
                .addHeader("Authorization", cred)
                .build();

        //get response that should return key issue ID to do attachment
        Response res = null;
        try {
            res = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] data = new String[0];
        try {
            data = res.body().string().split(",", 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        data[1] = data[1].replaceAll("[:\"key]","");
        System.out.println(" data[1]: "+ res);
        return data[1];
    }

    public static void importReportAttachment(String issueID) {

        String address = attachmentLink + issueID + "/attachments";
        String cred = Credentials.basic(username, password);
        File file = new File(reportLink);

        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("text/plain"), file))
                .build();

        Request request = new Request.Builder()
                .url(address)
                .post(formBody)
                .addHeader("Authorization", cred)
                .addHeader("X-Atlassian-Token", "no-check")
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
//            System.out.println(response.code() + " => " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




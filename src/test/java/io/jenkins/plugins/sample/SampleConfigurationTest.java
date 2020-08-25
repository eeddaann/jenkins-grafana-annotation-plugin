package io.jenkins.plugins.sample;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.jvnet.hudson.test.RestartableJenkinsRule;

public class SampleConfigurationTest {

    @Rule
    public RestartableJenkinsRule rr = new RestartableJenkinsRule();

    /**
     * Tries to exercise enough code paths to catch common mistakes:
     * <ul>
     * <li>missing {@code load}
     * <li>missing {@code save}
     * <li>misnamed or absent getter/setter
     * <li>misnamed {@code textbox}
     * </ul>
     */
    @Test
    public void uiAndStorage() {
        rr.then(r -> {
            assertNull("not set initially", SampleConfiguration.get().getGrafanaUrl());
            HtmlForm config = r.createWebClient().goTo("configure").getFormByName("config");
            HtmlTextInput urlTextbox = config.getInputByName("_.grafanaUrl");
            HtmlTextInput authTextbox = config.getInputByName("_.grafanaAuthentication");
            urlTextbox.setText("http://127.0.0.1:3000");
            authTextbox.setText("admin:admin");
            r.submit(config);
            assertEquals("global config page let us edit it", "http://127.0.0.1:3000", SampleConfiguration.get().getGrafanaUrl());
        });
        rr.then(r -> {
            assertEquals("still there after restart of Jenkins", "http://127.0.0.1:3000", SampleConfiguration.get().getGrafanaUrl());
            assertEquals("set correct authentication string", "Basic YWRtaW46YWRtaW4=", SampleConfiguration.get().getGrafanaAuthentication());
        });
    }

}

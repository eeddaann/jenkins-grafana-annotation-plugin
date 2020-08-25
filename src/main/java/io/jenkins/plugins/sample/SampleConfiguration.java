package io.jenkins.plugins.sample;

import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.GlobalConfiguration;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import java.net.HttpURLConnection;
import java.net.URL; 
import org.apache.commons.codec.binary.Base64;


/**
 * Example of Jenkins global configuration.
 */
@Extension
public class SampleConfiguration extends GlobalConfiguration {

    /** @return the singleton instance */
    public static SampleConfiguration get() {
        return GlobalConfiguration.all().get(SampleConfiguration.class);
    }

    private String grafanaAuthentication; // TODO: select from jenkins credentials
    private String grafanaUrl;

    public SampleConfiguration() {
        // When Jenkins is restarted, load any saved configuration from disk.
        load();
    }

    /** @return the currently configured Grafana credentials, if any */
    public String getGrafanaAuthentication() {
        return  grafanaAuthentication;
    }
    
    /** @return the currently configured Grafana Url, if any */
    public String getGrafanaUrl() {
        return  grafanaUrl;
    }

    public String transformAuth(String grafanaAuth) {
        if (grafanaAuth.contains(":")) {
            return "Basic " + new String(Base64.encodeBase64(grafanaAuth.getBytes()));
        } else {
            return "Bearer " + grafanaAuth;
        }
    }

    /**
     * Together with {@link #getGrafanaAuthentication}, binds to entry in {@code config.jelly}.
     * @param grafanaAuthentication the new value of this field
     */
    @DataBoundSetter
    public void setGrafanaAuthentication(String grafanaAuthentication) {
        this.grafanaAuthentication = transformAuth(grafanaAuthentication);
        save();
    }

    /**
     * Together with {@link #getGrafanaUrl}, binds to entry in {@code config.jelly}.
     * @param grafanaUrl the new value of this field
     */
    @DataBoundSetter
    public void setGrafanaUrl(String grafanaUrl) {
        this.grafanaUrl = grafanaUrl;
        save();
    }

    public FormValidation doCheckGrafanaAuthentication(@QueryParameter String value, @QueryParameter String grafanaUrl) {
        if (StringUtils.isEmpty(value)) {
            return FormValidation.warning("Please add authentication string: for Basic Auth (Admin:Admin), for API key just paste the key (eyJrIjoiT0tTcG1pUlY2RnVKZTFVaDFsNFZXdE9ZWmNrMkZYbk)");
        }
        try {
            URL url = new URL(grafanaUrl+"/api/org");
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty ("Authorization", transformAuth(value));
            int statusCode = http.getResponseCode();
            if (statusCode == 401) {
                return FormValidation.error("Unauthorized, please check your grafana credentials");
            } else if (statusCode != 200) {
                return FormValidation.error("not 200 http status code...");
            }
        } catch (Exception e)  {
            return FormValidation.error("failed to test connection to grafana - " + e.getMessage());
        }
        return FormValidation.ok("authenticated successfully!!");
    }

    public FormValidation doCheckGrafanaUrl(@QueryParameter String value) {
        if (StringUtils.isEmpty(value)) {
            return FormValidation.warning("Please specify url for Grafana. (eg. http://127.0.0.1:3000)");
        }
        try {
            URL url = new URL(value);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            int statusCode = http.getResponseCode();
        } catch (Exception e)  {
            return FormValidation.error("failed to test connection to grafana - " + e.getMessage());
        }
        return FormValidation.ok("reached grafana endpoint!");
    }

}

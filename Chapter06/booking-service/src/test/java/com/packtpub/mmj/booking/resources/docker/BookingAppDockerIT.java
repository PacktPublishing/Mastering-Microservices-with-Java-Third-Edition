package com.packtpub.mmj.booking.resources.docker;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 * @author Sourabh Sharma
 */
@Category(DockerIT.class)
public class BookingAppDockerIT {

    /**
     *
     * @throws IOException
     */
    @Test
    public void testConnection() throws IOException {
        String baseUrl = System.getProperty("service.url");
        URL serviceUrl = new URL(baseUrl + "v1/booking/1");
        HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
        int responseCode = connection.getResponseCode();
        assertEquals(200, responseCode);
    }
}

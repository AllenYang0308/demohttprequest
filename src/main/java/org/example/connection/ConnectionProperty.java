
package org.example.connection;

import lombok.Data;

@Data
public class ConnectionProperty {
    String apiUrl;
    String method;
    String postData;
    String certFile;
}

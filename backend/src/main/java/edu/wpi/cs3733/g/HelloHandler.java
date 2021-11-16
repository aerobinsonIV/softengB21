package edu.wpi.cs3733.g;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

class HelloRequest {
    String arg;

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public HelloRequest(String arg) {
        this.arg = arg;
    }

    public HelloRequest() {
    }

    @Override
    public String toString() {
        return "HelloRequest{" +
                "arg='" + arg + '\'' +
                '}';
    }
}

public class HelloHandler implements RequestHandler<HelloRequest, HelloRequest> {
    @Override
    public HelloRequest handleRequest(HelloRequest input, Context context) {
        context.getLogger().log("Handling: " + input.arg);
        return new HelloRequest("Hello, You Sent: " + input.arg);
    }
}

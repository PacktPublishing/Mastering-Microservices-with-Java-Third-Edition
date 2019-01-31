package com.packtpub.mmj.grpc.server;

import com.packtpub.mmj.grpc.server.services.EmployeeService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

  public static void main(String[] arg) {
    try {
      Server server = ServerBuilder.forPort(8080)
          .addService(new EmployeeService())
          .build();
      System.out.println("Starting gRPC Server Service ...");
      server.start();
      System.out.println("Server has started at port: 8080");
      System.out.println("Following services are available:  ");
      server.getServices().stream()
          .forEach(
              s -> System.out.println("Service Name: " + s.getServiceDescriptor().getName())
          );
      server.awaitTermination();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

package com.packtpub.mmj.grpc.server.services;

import com.packtpub.mmj.grpc.server.Employee;
import com.packtpub.mmj.grpc.server.EmployeeId;
import com.packtpub.mmj.grpc.server.EmployeeList;
import com.packtpub.mmj.grpc.server.EmployeeServiceGrpc.EmployeeServiceImplBase;
import com.packtpub.mmj.grpc.server.Empty;
import com.packtpub.mmj.grpc.server.Response;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class EmployeeService extends EmployeeServiceImplBase {

  private static final ConcurrentMap<String, Employee> entities;
  private static long counter = 2;

  static {
    Employee.Address address = Employee.Address.newBuilder().setHouseNo("604")
        .setStreet("Park View")
        .setCity("Atlanta")
        .setState("State")
        .setCountry("US").build();
    entities = new ConcurrentHashMap<>(Map.ofEntries(
        new SimpleEntry<>("1",
            Employee.newBuilder().setId(1L).setFirstName("John").setLastName("Mathews")
                .setDeptId(1L).setSalary(100000)
                .setAddress(address)
                .build()
        )
    ));
  }

  @Override
  public void create(Employee request,
      io.grpc.stub.StreamObserver<Response> responseObserver) {
    request.toBuilder().setId(counter);
    entities.put(String.valueOf(counter), request);
    counter = counter + 1;
    responseObserver
        .onNext(Response
            .newBuilder()
            .setCode(201)
            .setMessage("CREATED")
            .build());
    responseObserver.onCompleted();
  }

  /**
   *
   */
  @Override
  public void list(Empty request,
      io.grpc.stub.StreamObserver<EmployeeList> responseObserver) {
    responseObserver.onNext(EmployeeList.newBuilder().addAllEmployees(entities.values()).build());
    responseObserver.onCompleted();
  }

  /**
   *
   */
  @Override
  public void update(Employee request,
      io.grpc.stub.StreamObserver<Response> responseObserver) {
    String id = String.valueOf(request.getId());
    if (entities.keySet().contains(id)) {
      entities.put(id, request);
      responseObserver
          .onNext(Response
              .newBuilder()
              .setCode(200)
              .setMessage("UPDATED")
              .build());
    } else {
      responseObserver
          .onNext(Response
              .newBuilder()
              .setCode(404)
              .setMessage("NOT_FOUND")
              .build());
    }

    responseObserver.onCompleted();
  }

  /**
   *
   */
  @Override
  public void delete(EmployeeId request,
      io.grpc.stub.StreamObserver<Response> responseObserver) {
    String id = String.valueOf(request.getId());
    if (entities.keySet().contains(id)) {
      entities.remove(id);
      responseObserver
          .onNext(Response
              .newBuilder()
              .setCode(200)
              .setMessage("DELETED")
              .build());
    } else {
      responseObserver
          .onNext(Response
              .newBuilder()
              .setCode(404)
              .setMessage("NOT_FOUND")
              .build());
    }
    responseObserver.onCompleted();
  }
}

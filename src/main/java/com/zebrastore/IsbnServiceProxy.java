package com.zebrastore;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient( configKey = "isbn-service" )
@Path("/api/isbn")
public interface IsbnServiceProxy {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  IsbnThirteen generateIsbnNumbers();
}

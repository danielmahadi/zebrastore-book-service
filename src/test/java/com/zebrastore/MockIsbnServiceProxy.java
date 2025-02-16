package com.zebrastore;

import io.quarkus.test.Mock;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Mock
@RestClient
public class MockIsbnServiceProxy implements IsbnServiceProxy {

  @Override
  public IsbnThirteen generateIsbnNumbers() {
    IsbnThirteen isbnThirteen = new IsbnThirteen();
    isbnThirteen.isbn13 = "13-mock";
    return isbnThirteen;
  }
}


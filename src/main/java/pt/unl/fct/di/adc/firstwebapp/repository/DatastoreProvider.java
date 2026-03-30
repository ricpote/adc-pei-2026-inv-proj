package pt.unl.fct.di.adc.firstwebapp.repository;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;

public class DatastoreProvider {

  private static final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

  private DatastoreProvider() {
  }

  public static Datastore getDatastore() {
    return datastore;
  }
}
package org.opencastproject.distribution.streaming.endpoint;
import static javax.servlet.http.HttpServletResponse.SC_NO_CONTENT;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static javax.ws.rs.core.Response.status;
import org.opencastproject.job.api.JaxbJob;
import org.opencastproject.job.api.Job;
import org.opencastproject.job.api.JobProducer;
import org.opencastproject.mediapackage.MediaPackage;
import org.opencastproject.mediapackage.MediaPackageParser;
import org.opencastproject.rest.AbstractJobProducerEndpoint;
import org.opencastproject.serviceregistry.api.ServiceRegistry;
import org.opencastproject.util.doc.rest.RestParameter;
import org.opencastproject.util.doc.rest.RestParameter.Type;
import org.opencastproject.util.doc.rest.RestQuery;
import org.opencastproject.util.doc.rest.RestResponse;
import org.opencastproject.util.doc.rest.RestService;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.opencastproject.distribution.api.StreamingDistributionService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Set;

/**
 * Rest endpoint for distributing media to the local streaming distribution channel.
 */
@Path(value = "/") @RestService(name = "localdistributionservice", title = "Local Distribution Service", abstractText = "This service distributes media packages to the Matterhorn feed and engage services.", notes = { "All paths above are relative to the REST endpoint base (something like http://your.server/files)", "If the service is down or not working it will return a status 503, this means the the underlying service is " + "not working and is either restarting or has failed", "A status code 500 means a general failure has occurred which is not recoverable and was not anticipated. In " + "other words, there is a bug! You should file an error report with your server logs from the time when the " + "error occurred: <a href=\"https://opencast.jira.com\">Opencast Issue Tracker</a>" }) public class StreamingDistributionRestService extends AbstractJobProducerEndpoint {
  /** The logger */
  private static final Logger logger = LoggerFactory.getLogger(StreamingDistributionRestService.class);

  /** The distribution service */
  protected StreamingDistributionService service;

  /** The service registry */
  protected ServiceRegistry serviceRegistry = null;

  /**
   * OSGi activation callback
   *
   * @param cc
   *          this component's context
   */
  public void activate(ComponentContext cc) {
  }

  /**
   * Callback from the OSGi declarative services to set the service registry.
   *
   * @param serviceRegistry
   *          the service registry
   */
  protected void setServiceRegistry(ServiceRegistry serviceRegistry) {
    this.serviceRegistry = serviceRegistry;
  }

  /**
   * @param service
   *          the service to set
   */
  public void setService(StreamingDistributionService service) {
    this.service = service;
  }

  @POST @Path(value = "/") @Produces(value = MediaType.TEXT_XML) @RestQuery(name = "distribute", description = "Distribute a media package element to this distribution channel", returnDescription = "The job that can be used to track the distribution", restParameters = { @RestParameter(name = "mediapackage", isRequired = true, description = "The mediapackage", type = Type.TEXT), @RestParameter(name = "channelId", isRequired = true, description = "The publication channel ID", type = Type.TEXT), @RestParameter(name = "elementIds", isRequired = true, description = "The elements to distribute as Json Array[\'IdOne\',\'IdTwo\']", type = Type.STRING) }, reponses = { @RestResponse(responseCode = SC_OK, description = "An XML representation of the distribution job"), @RestResponse(responseCode = SC_NO_CONTENT, description = "There is no streaming distribution service available") }) public Response distribute(@FormParam(value = "mediapackage") String mediaPackageXml, @FormParam(value = "channelId") String channelId, @FormParam(value = "elementIds") String elementIds) throws Exception {
    Job job = null;
    try {
      Gson gson = new Gson();
      Set<String> setElementIds = gson.fromJson(elementIds, new TypeToken<Set<String>>() { }.getType());
      MediaPackage mediapackage = MediaPackageParser.getFromXml(mediaPackageXml);
      job = service.distribute(channelId, mediapackage, setElementIds);
      if (job == null) {
        return Response.noContent().build();
      }
      return Response.ok(new JaxbJob(job)).build();
    } catch (IllegalArgumentException e) {
      logger.debug("Unable to distribute element: {}", e.getMessage());
      return status(Status.BAD_REQUEST).build();
    } catch (Exception e) {
      logger.warn("Error distributing element", e);
      return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  @POST @Path(value = "/retract") @Produces(value = MediaType.TEXT_XML) @RestQuery(name = "retract", description = "Retract a media package element from this distribution channel", returnDescription = "The job that can be used to track the retraction", restParameters = { @RestParameter(name = "mediapackage", isRequired = true, description = "The mediapackage", type = Type.TEXT), @RestParameter(name = "channelId", isRequired = true, description = "The publication channel ID", type = Type.TEXT), @RestParameter(name = "elementIds", isRequired = true, description = "The elements to retract as Json Array[\'IdOne\',\'IdTwo\']", type = Type.STRING) }, reponses = { @RestResponse(responseCode = SC_OK, description = "An XML representation of the retraction job"), @RestResponse(responseCode = SC_NO_CONTENT, description = "There is no streaming distribution service available") }) public Response retract(@FormParam(value = "mediapackage") String mediaPackageXml, @FormParam(value = "channelId") String channelId, @FormParam(value = "elementIds") String elementIds) throws Exception {
    Job job = null;
    try {
      Gson gson = new Gson();
      Set<String> setElementIds = gson.fromJson(elementIds, new TypeToken<Set<String>>() { }.getType());
      MediaPackage mediapackage = MediaPackageParser.getFromXml(mediaPackageXml);
      job = service.retract(channelId, mediapackage, setElementIds);
      if (job == null) {
        return Response.noContent().build();
      }
      return Response.ok(new JaxbJob(job)).build();
    } catch (IllegalArgumentException e) {
      logger.debug("Unable to retract element: {}", e.getMessage());
      return status(Status.BAD_REQUEST).build();
    } catch (Exception e) {
      logger.warn("Unable to retract mediapackage \'{}\' from streaming channel: {}", mediaPackageXml, e);
      return Response.serverError().status(Status.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see org.opencastproject.rest.AbstractJobProducerEndpoint#getService()
   */
  @Override public JobProducer getService() {
    if (service instanceof JobProducer) {
      return (JobProducer) service;
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   *
   * @see org.opencastproject.rest.AbstractJobProducerEndpoint#getServiceRegistry()
   */
  @Override public ServiceRegistry getServiceRegistry() {
    return serviceRegistry;
  }
}
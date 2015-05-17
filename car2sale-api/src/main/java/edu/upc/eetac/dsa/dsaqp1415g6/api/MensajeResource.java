package edu.upc.eetac.dsa.dsaqp1415g6.api;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sql.DataSource;
import javax.ws.rs.Path;

import java.util.UUID;
import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Application;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.upc.eetac.dsa.dsaqp1415g6.api.model.Anuncio;
import edu.upc.eetac.dsa.dsaqp1415g6.api.model.Mensaje;
import edu.upc.eetac.dsa.dsaqp1415g6.api.model.MensajeCollection;
@Path("/mensajes")
public class MensajeResource {
	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String GET_MENSAJES_QUERY = "select * from mensajes where usuariorecibe=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?";
	private String GET_MENSAJES_QUERY_LAST = "select * from mensajes where usuariorecibe=? and creation_timestamp > ? ORDER BY creation_timestamp DESC";
	private String GET_MENSAJE_BY_ID_QUERY = "SELECT * FROM mensajes WHERE idmensaje=?";

	
	@GET
	@Produces(MediaType.MENSAJES_API_MENSAJE_COLLECTION)
	public MensajeCollection getMensajes(@QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		MensajeCollection mensajes = new MensajeCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			boolean updateFromLast = after > 0;
			stmt = updateFromLast ? conn
					.prepareStatement(GET_MENSAJES_QUERY_LAST) : conn
					.prepareStatement(GET_MENSAJES_QUERY);
			if (updateFromLast) {
				
				stmt.setTimestamp(2, new Timestamp(after));
				stmt.setString(1, security.getUserPrincipal().getName());
				
			} else {
				if (before > 0){
					stmt.setTimestamp(2, new Timestamp(before));
					stmt.setString(1, security.getUserPrincipal().getName());
				
				}
				else{
					
					stmt.setTimestamp(2, null);
				    length = (length <= 0) ? 10 : length;
				    stmt.setInt(3, length);
				    stmt.setString(1,security.getUserPrincipal().getName());
					
				}
			}
		
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;	
		
			while (rs.next()) {
				Mensaje mensaje = new Mensaje();
				mensaje.setIdmensaje(rs.getInt("idmensaje"));
				mensaje.setUsuarioenvia(rs.getString("usuarioenvia"));
				mensaje.setUsuariorecibe(rs.getString("usuariorecibe"));
				mensaje.setAnuncio(rs.getInt("anuncio"));
				mensaje.setMensaje(rs.getString("mensaje"));
				mensaje.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				mensaje.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				mensaje.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					mensajes.setNewestTimestamp(mensaje.getCreation_timestamp());
				}
				mensajes.addMensaje(mensaje);
			}
			mensajes.setOldestTimestamp(oldestTimestamp);
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
	 
		return mensajes;
	}
	
	@GET
	@Path("/{idmensaje}")
	@Produces(MediaType.MENSAJES_API_MENSAJE)
	public Mensaje getMensaje(@PathParam("idmensaje") String idmensaje) {
		Mensaje mensaje = new Mensaje();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		
		
		try {
			stmt = conn.prepareStatement(GET_MENSAJE_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(idmensaje));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				mensaje.setIdmensaje(rs.getInt("idmensaje"));
				mensaje.setUsuarioenvia(rs.getString("usuarioenvia"));
				mensaje.setUsuariorecibe(rs.getString("usuariorecibe"));
				mensaje.setAnuncio(rs.getInt("anuncio"));
				mensaje.setMensaje(rs.getString("mensaje"));
				mensaje.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				mensaje.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
			}
			else {
				throw new NotFoundException("No hay mensaje con anuncio="
						+ idmensaje);
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		return mensaje;
	}
	
	

}


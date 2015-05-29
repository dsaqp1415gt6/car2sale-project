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
import edu.upc.eetac.dsa.dsaqp1415g6.api.model.AnuncioCollection;

@Path("/anuncios")
public class AnuncioResource {
	@Context
	private Application app;
	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	private String GET_ANUNCIOS_QUERY = "SELECT * FROM anuncio WHERE creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp asc limit ?";
	private String GET_ANUNCIOS_QUERY_FROM_LAST = "SELECT * FROM anuncio WHERE creation_timestamp > ? ORDER BY creation_timestamp DESC limit ?";
	private String GET_ANUNCIO_BY_ID_QUERY = "SELECT * FROM anuncio WHERE idanuncio=?";
	private String GET_ANUNCIO_BY_ID_MARCA = "SELECT * FROM anuncio WHERE marca=?";
	private String GET_ANUNCIO_BY_ID_PRECIO= "select * from anuncio where precio> ?  order by precio asc";
	private String GET_ANUNCIO_BY_ID_KM= "select * from anuncio where km> ?  order by km asc";
	private String UPDATE_CONTADOR_ANUNCIO_BY_ID_QUERY="UPDATE anuncio SET anuncio.cont = anuncio.cont + 1 where idanuncio = ?";
	private String GET_ANUNCIO_BY_USERNAME= "select * from anuncio where username=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?";
	private String GET_ANUNCIO_BY_USERNAME_LAST= "select * from anuncio where username=? and creation_timestamp > ? ORDER BY creation_timestamp DESC";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_QUERY = "select * from anuncio where marca=? and modelo=? and creation_timestamp < ifnull(?, now()) order by creation_timestamp desc limit ?";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_QUERY_FROM_LAST = "select * from anuncio where marca=? and modelo=? and creation_timestamp > ? order by creation_timestamp desc";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_QUERY = "select * from anuncio where marca=? and modelo=? and km < ? order by km asc";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PRECIO_QUERY = "select * from anuncio where marca=? and modelo=? and precio < ? and km <= ? order by precio asc";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_MASPRECIO_QUERY = "select * from anuncio where marca=? and modelo=? and precio >= ? and km <= ? order by precio asc";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PRECIO_AND_PROVINCIA_QUERY = "select * from anuncio where marca=? and modelo=? and km< ifnull(?,km) and precio< ifnull(?,precio) and provincia=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PRECIO_AND_PROVINCIA_QUERY_LAST = "select * from anuncio where marca=? and modelo=? and km >? and precio > ? and provincia=? and creation_timestamp  > ? ORDER BY creation_timestamp DESC";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PRECIO_QUERY = "select * from anuncio where marca=? and modelo=? and precio < ? order by precio asc";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_MASPRECIO_QUERY = "select * from anuncio where marca=? and modelo=? and precio >= ? order by precio asc";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PRECIO_PROVINCIA_QUERY = "select * from anuncio where marca=? and modelo=? and precio < ifnull(?,precio) and provincia=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PRECIO_PROVINCIA_QUERY_LAST = "select * from anuncio where marca=? and modelo=? and precio > ? and provincia=? and creation_timestamp > ? ORDER BY creation_timestamp DESC";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PROVINCIA_QUERY = "select * from anuncio where marca=? and modelo=? and provincia=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PROVINCIA_QUERY_LAST = "select * from anuncio where marca=? and modelo=? and provincia=? and creation_timestamp > ? ORDER BY creation_timestamp DESC";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PROVINCIA_QUERY = "select * from anuncio where marca=? and modelo=? and km< ifnull (?, km) and provincia=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?";
	private String GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PROVINCIA_QUERY_LAST = "select * from anuncio where marca=? and modelo=? and km> ? and provincia=? and creation_timestamp> ? ORDER BY creation_timestamp DESC";
	private String INSERT_ANUNCIO_QUERY="insert into anuncio (imagen, cont, username, titulo, descripcion, marca, modelo, km, precio, provincia) values (?,?,?,?,?,?,?,?,?,?)";
	private String DELETE_ANUNCIO_QUERY = "delete from anuncio where idanuncio=?";
	private String UPDATE_ANUNCIO_QUERY = "update anuncio set imagen=ifnull(?,imagen), cont=ifnull(?,cont),  titulo=ifnull(?, titulo), descripcion=ifnull(?, descripcion), marca=ifnull(?, marca), modelo=ifnull(?, modelo), km=ifnull(?, km), precio=ifnull(?, precio), provincia=ifnull(?, provincia)  where idanuncio=?";
	
	@GET
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getAnuncios(@QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		System.out.println(before);
		System.out.println(after);
		System.out.println(length);
		try {
			boolean updateFromLast = after > 0;
			System.out.println(updateFromLast);
			stmt = updateFromLast ? conn
					.prepareStatement(GET_ANUNCIOS_QUERY_FROM_LAST) : conn
					.prepareStatement(GET_ANUNCIOS_QUERY);
			if (updateFromLast) {
				stmt.setTimestamp(1, new Timestamp(before));
				length = (length <= 0) ? 10 : length;
				stmt.setInt(2, length);
			}	 else {
				if (before > 0)
					stmt.setTimestamp(1, new Timestamp(before));
					else
					stmt.setTimestamp(1, null);
					length = (length <= 0) ? 10 : length;
					stmt.setInt(2, length);

			}
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp=0;
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");				
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("last_modified").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getLast_modified());
					
				}
				anuncios.addAnuncio(anuncio);
			}
			anuncios.setOldestTimestamp(oldestTimestamp);
		
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

		return anuncios;
	}
	
	public void AumentarContador (String idanuncio){
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_CONTADOR_ANUNCIO_BY_ID_QUERY);		
			stmt.setInt(1, Integer.valueOf(idanuncio));
			
			stmt.executeUpdate();
			
			
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
	 
		
	}
	//FUNCION EXCLUSIVA PARA APP ANDROID
	@GET
	@Path("/marcas/{marca}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getMarca (@PathParam("marca") String marca) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_ANUNCIO_BY_ID_MARCA);
			stmt.setString(1, String.valueOf(marca));
		
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				anuncios.addAnuncio(anuncio);
				
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
	 
		return anuncios;
	}
	// FUNCION EXCLUSIVA PARA APP ANDROID
	@GET
	@Path("/precios/{precio}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getPrecios (@PathParam("precio") String precio) {
		AnuncioCollection anuncios = new AnuncioCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_ANUNCIO_BY_ID_PRECIO);
			stmt.setString(1, String.valueOf(precio));
		
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				anuncios.addAnuncio(anuncio);
				
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
	 
		return anuncios;
	}
	
	// FUNCION EXCLUSIVA PARA APP ANDROID
		@GET
		@Path("/kms/{km}")
		@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
		public AnuncioCollection getKms (@PathParam("km") String km) {
			AnuncioCollection anuncios = new AnuncioCollection();
			
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
		 
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(GET_ANUNCIO_BY_ID_KM);
				stmt.setString(1, String.valueOf(km));
			
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Anuncio anuncio = new Anuncio();
					anuncio.setIdanuncio(rs.getInt("idanuncio"));
					anuncio.setFilename(rs.getString("imagen") + ".png");
					anuncio.setImageURL(app.getProperties().get("imgBaseURL")
							+ anuncio.getFilename());
					anuncio.setContador(rs.getInt("cont"));
					anuncio.setUsername(rs.getString("username"));
					anuncio.setTitulo(rs.getString("titulo"));
					anuncio.setDescripcion(rs.getString("descripcion"));
					anuncio.setMarca(rs.getString("marca"));
					anuncio.setModelo(rs.getString("modelo"));
					anuncio.setKm(rs.getInt("km"));
					anuncio.setPrecio(rs.getInt("precio"));
					anuncio.setProvincia(rs.getString("provincia"));
					anuncio.setLast_modified(rs.getTimestamp("last_modified")
							.getTime());
					anuncio.setCreation_timestamp(rs
							.getTimestamp("creation_timestamp").getTime());
					anuncios.addAnuncio(anuncio);
					
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
		 
			return anuncios;
		}
		 
	 
	@GET
	@Path("/{idanuncio}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO)
	public Anuncio getAnuncio(@PathParam("idanuncio") String idanuncio) {
		Anuncio anuncio = new Anuncio();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		
		
		try {
			stmt = conn.prepareStatement(GET_ANUNCIO_BY_ID_QUERY);
			stmt.setInt(1, Integer.valueOf(idanuncio));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
			}
			else {
				throw new NotFoundException("No hay anuncio con idanuncio="
						+ idanuncio);
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
		AumentarContador(idanuncio);
		return anuncio;
	}
	
	@GET
	@Path("/misanuncios")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getMisAnuncios(@QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
		
	
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
					.prepareStatement(GET_ANUNCIO_BY_USERNAME_LAST) : conn
					.prepareStatement(GET_ANUNCIO_BY_USERNAME);
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
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");				
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getCreation_timestamp());
				}
				anuncios.addAnuncio(anuncio);
			}
			anuncios.setOldestTimestamp(oldestTimestamp);
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
	 
		return anuncios;
}
	@GET
	@Path("/{marca}/{modelo}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getModelos (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
		
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
					.prepareStatement( GET_ANUNCIO_BY_MARCA_AND_MODELO_QUERY_FROM_LAST) : conn
					.prepareStatement( GET_ANUNCIO_BY_MARCA_AND_MODELO_QUERY);
			if (updateFromLast) {
				
				stmt.setTimestamp(3, new Timestamp(after));
				stmt.setString(1, String.valueOf(marca));
				stmt.setString(2, String.valueOf(modelo));
			} else {
				if (before > 0){
					stmt.setTimestamp(3, new Timestamp(before));
				stmt.setString(1, String.valueOf(marca));
				stmt.setString(2, String.valueOf(modelo));
				
				}
				else{
					
					stmt.setTimestamp(3, null);
				    length = (length <= 0) ? 10 : length;
				    stmt.setInt(4, length);
				    stmt.setString(1, marca);
				    stmt.setString(2, modelo);
				    
				
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;			
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getCreation_timestamp());
				}
				anuncios.addAnuncio(anuncio);
				
			}
			anuncios.setOldestTimestamp(oldestTimestamp);
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
	 
		return anuncios;
	}
	
	@GET
	@Path("/{marca}/{modelo}/{km}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getKm (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("km") Integer km) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_QUERY);		
			stmt.setString(1, String.valueOf(marca));
			stmt.setString(2, String.valueOf(modelo));
			stmt.setInt(3, Integer.valueOf(km));
			ResultSet rs = stmt.executeQuery();
		
						
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				
			
				anuncios.addAnuncio(anuncio);				
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
	 
		return anuncios;
	}
	
	@GET
	@Path("/{marca}/{modelo}/{precio}/{km}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getPrecio (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("km") Integer km, @PathParam("precio") Integer precio) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			if (precio<70000){
				
				stmt = conn.prepareStatement( GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PRECIO_QUERY);
				
			}
				else if (precio>=70000){
					
				stmt = conn.prepareStatement( GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_MASPRECIO_QUERY);
			}
			
			stmt.setString(1, String.valueOf(marca));
			stmt.setString(2, String.valueOf(modelo));
			stmt.setInt(3, Integer.valueOf(precio));
			stmt.setInt(4, Integer.valueOf(km));
			ResultSet rs = stmt.executeQuery();
													
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
			
					
				
				anuncios.addAnuncio(anuncio);					
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
	 
		return anuncios;
	}
	@GET
	@Path("/{marca}/{modelo}/{km}/{precio}/{provincia}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getProvincia (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("km") Integer km, @PathParam("precio") Integer precio, @PathParam("provincia") String provincia, @QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
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
						.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PRECIO_AND_PROVINCIA_QUERY_LAST) : conn
						.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PRECIO_AND_PROVINCIA_QUERY);
				if (updateFromLast) {
					
					stmt.setTimestamp(6, new Timestamp(after));
					stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setInt(3, Integer.valueOf(km));
					stmt.setInt(4, Integer.valueOf(precio));
					stmt.setString(5, String.valueOf(provincia));
				} else {
					if (before > 0){
						stmt.setTimestamp(6, new Timestamp(before));
						stmt.setString(1, String.valueOf(marca));
						stmt.setString(2, String.valueOf(modelo));
						stmt.setInt(3, Integer.valueOf(km));
						stmt.setInt(4, Integer.valueOf(precio));
						stmt.setString(5, String.valueOf(provincia));
					
					}
					else{
						
						stmt.setTimestamp(6, null);
					    length = (length <= 0) ? 10 : length;
					    stmt.setInt(7, length);
					    stmt.setString(1, String.valueOf(marca));
						stmt.setString(2, String.valueOf(modelo));
						stmt.setInt(3, Integer.valueOf(km));
						stmt.setInt(4, Integer.valueOf(precio));
						stmt.setString(5, String.valueOf(provincia));
					    
					
					}
				}
				
				ResultSet rs = stmt.executeQuery();
				boolean first = true;
				long oldestTimestamp = 0;
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getCreation_timestamp());
				}
				anuncios.addAnuncio(anuncio);
			}
			anuncios.setOldestTimestamp(oldestTimestamp);
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
	 
		return anuncios;
	}
	
	@GET
	@Path("/precios/{marca}/{modelo}/{precio}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getModeloPrecios (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("precio") Integer precio) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			if (precio<70000){
			
			stmt = conn.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PRECIO_QUERY);
			
			}
			else if (precio>=70000){
				
				stmt = conn.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_MASPRECIO_QUERY);
			}
			stmt.setString(1, String.valueOf(marca));
			stmt.setString(2, String.valueOf(modelo));
			stmt.setInt(3, Integer.valueOf(precio));
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				
				anuncios.addAnuncio(anuncio);					
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
	 
		return anuncios;
	}
	@GET
	@Path("/precios/{marca}/{modelo}/{precio}/{provincia}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getPrecioProvincia (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("precio") Integer precio, @PathParam("provincia") String provincia, @QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
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
					.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PRECIO_PROVINCIA_QUERY_LAST) : conn
					.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PRECIO_PROVINCIA_QUERY);
			if (updateFromLast) {
				
				stmt.setTimestamp(5, new Timestamp(after));
				stmt.setString(1, String.valueOf(marca));
				stmt.setString(2, String.valueOf(modelo));
				stmt.setInt(3, Integer.valueOf(precio));
				stmt.setString(4, String.valueOf(provincia));
			} else {
				if (before > 0){
					stmt.setTimestamp(5, new Timestamp(before));
					stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setInt(3, Integer.valueOf(precio));
					stmt.setString(4, String.valueOf(provincia));
				
				}
				else{
					
					stmt.setTimestamp(5, null);
				    length = (length <= 0) ? 10 : length;
				    stmt.setInt(6, length);
				    stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setInt(3, Integer.valueOf(precio));
					stmt.setString(4, String.valueOf(provincia));
				    
				
				}
			}
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getCreation_timestamp());
				}
				anuncios.addAnuncio(anuncio);
			}	
			anuncios.setOldestTimestamp(oldestTimestamp);
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
	 
		return anuncios;
	}
	
	@GET
	@Path("/provincias/{marca}/{modelo}/{provincia}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getModeloPrecios (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("provincia") String provincia,  @QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
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
					.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PROVINCIA_QUERY_LAST) : conn
					.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_PROVINCIA_QUERY);
			if (updateFromLast) {
				
				stmt.setTimestamp(4, new Timestamp(after));
				stmt.setString(1, String.valueOf(marca));
				stmt.setString(2, String.valueOf(modelo));
				stmt.setString(3, String.valueOf(provincia));
			} else {
				if (before > 0){
					stmt.setTimestamp(4, new Timestamp(before));
					stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setString(3, String.valueOf(provincia));
				
				}
				else{
					
					stmt.setTimestamp(4, null);
				    length = (length <= 0) ? 10 : length;
				    stmt.setInt(5, length);
				    stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setString(3, String.valueOf(provincia));
				    
				
				}
			}
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;			
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getCreation_timestamp());
				}
				anuncios.addAnuncio(anuncio);
			}	
			anuncios.setOldestTimestamp(oldestTimestamp);			
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
	 
		return anuncios;
	}
	@GET
	@Path("/provincias/{marca}/{modelo}/{km}/{provincia}")
	@Produces(MediaType.ANUNCIOS_API_ANUNCIO_COLLECTION)
	public AnuncioCollection getKmProvincia (@PathParam("marca") String marca, @PathParam("modelo") String modelo, @PathParam("km") Integer km, @PathParam("provincia") String provincia, @QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
		AnuncioCollection anuncios = new AnuncioCollection();
	 
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
					.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PROVINCIA_QUERY_LAST) : conn
					.prepareStatement(GET_ANUNCIO_BY_MARCA_AND_MODELO_AND_KM_AND_PROVINCIA_QUERY);
			if (updateFromLast) {
				
				stmt.setTimestamp(5, new Timestamp(after));
				stmt.setString(1, String.valueOf(marca));
				stmt.setString(2, String.valueOf(modelo));
				stmt.setInt(3, Integer.valueOf(km));
				stmt.setString(4, String.valueOf(provincia));
			} else {
				if (before > 0){
					stmt.setTimestamp(5, new Timestamp(before));
					stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setInt(3, Integer.valueOf(km));
					stmt.setString(4, String.valueOf(provincia));
				
				}
				else{
					
					stmt.setTimestamp(5, null);
				    length = (length <= 0) ? 10 : length;
				    stmt.setInt(6, length);
				    stmt.setString(1, String.valueOf(marca));
					stmt.setString(2, String.valueOf(modelo));
					stmt.setInt(3, Integer.valueOf(km));
					stmt.setString(4, String.valueOf(provincia));			    				
				}
			}
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;			
			while (rs.next()) {
				Anuncio anuncio = new Anuncio();
				anuncio.setIdanuncio(rs.getInt("idanuncio"));
				anuncio.setFilename(rs.getString("imagen") + ".png");
				anuncio.setImageURL(app.getProperties().get("imgBaseURL")
						+ anuncio.getFilename());
				anuncio.setContador(rs.getInt("cont"));
				anuncio.setUsername(rs.getString("username"));
				anuncio.setTitulo(rs.getString("titulo"));
				anuncio.setDescripcion(rs.getString("descripcion"));
				anuncio.setMarca(rs.getString("marca"));
				anuncio.setModelo(rs.getString("modelo"));
				anuncio.setKm(rs.getInt("km"));
				anuncio.setPrecio(rs.getInt("precio"));
				anuncio.setProvincia(rs.getString("provincia"));
				anuncio.setLast_modified(rs.getTimestamp("last_modified")
						.getTime());
				anuncio.setCreation_timestamp(rs
						.getTimestamp("creation_timestamp").getTime());
				oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
				anuncio.setLast_modified(oldestTimestamp);
				if (first) {
					first = false;
					anuncios.setNewestTimestamp(anuncio.getCreation_timestamp());
				}
				anuncios.addAnuncio(anuncio);
			}	
			anuncios.setOldestTimestamp(oldestTimestamp);		
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
	 
		return anuncios;
	}
	@DELETE
	@Path("/{idanuncio}")
	public void deleteAnuncio(@PathParam("idanuncio") String idanuncio) {
		if (!security.isUserInRole("administrador")) {
			validateUser(idanuncio);
		}
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_ANUNCIO_QUERY);
			stmt.setInt(1, Integer.valueOf(idanuncio));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("No hay anuncio con idanuncio="
						+ idanuncio);
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
	}
	private void validateUser(String idanuncio) {
	    Anuncio anuncio = getAnuncio(idanuncio);
	    String username = anuncio.getUsername();
		if (!security.getUserPrincipal().getName()
				.equals(username))
			throw new ForbiddenException(
					"No se le permite modificar este anuncio.");
	}
	@PUT
	@Path("/{idanuncio}")
	@Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
	public Anuncio updateAnuncio(@PathParam("idanuncio") String idanuncio,
			@FormDataParam("titulo") String titulo, 
			@FormDataParam("marca") String marca,
			@FormDataParam("modelo") String modelo,
			@FormDataParam("km") Integer km,
			@FormDataParam("precio") Integer precio,
			@FormDataParam("provincia") String provincia,
			@FormDataParam("descripcion") String descripcion,
			@FormDataParam("image") InputStream image,
			@FormDataParam("image") FormDataContentDisposition fileDisposition) {
		validateUser(idanuncio);
	//	validateUpdateAnuncio(anuncio);
		UUID uuid = writeAndConvertImage(image);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(UPDATE_ANUNCIO_QUERY);
			stmt.setString(1, uuid.toString());
			stmt.setInt(2, 0);
			stmt.setString(3, titulo);
			stmt.setString(4, descripcion);
			stmt.setString(5, marca);
			stmt.setString(6, modelo);
			stmt.setInt(7, km);
			stmt.setInt(8, precio);
			stmt.setString(9, provincia);
			stmt.setInt(10, Integer.valueOf(idanuncio));
	 
			stmt.executeUpdate();
			
	 
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
		Anuncio anuncio = new Anuncio();
		anuncio.setFilename(uuid.toString() + ".png");
		anuncio.setImageURL(app.getProperties().get("imgBaseURL")
				+ anuncio.getFilename());
	 
		return anuncio;
		}
	private void validateUpdateAnuncio(Anuncio anuncio) {
		if (anuncio.getTitulo() != null && anuncio.getTitulo().length() > 100)
			throw new BadRequestException(
					"El titulo del anuncio no puede ser mayor de 100 caracteres.");
		if (anuncio.getDescripcion() != null && anuncio.getDescripcion().length() > 500)
			throw new BadRequestException(
					"La descripcion del anuncio no puede ser mayor de 500 caracteres.");
		if (anuncio.getMarca() != null && anuncio.getMarca().length() > 20)
			throw new BadRequestException(
					"La marca del coche no puede ser mayor de 20 caracteres.");
		if (anuncio.getModelo() != null && anuncio.getModelo().length() > 40)
			throw new BadRequestException(
					"El modelo del coche no puede ser mayor de 40 caracteres.");
		if (anuncio.getProvincia() != null && anuncio.getProvincia().length() > 50)
			throw new BadRequestException(
					"La provincia donde se vende el coche no puede ser mayor de 50 caracteres.");
		
		}


	@POST
	@Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
	public Anuncio uploadAnuncio (@FormDataParam("titulo") String titulo, 
			@FormDataParam("marca") String marca,
			@FormDataParam("modelo") String modelo,
			@FormDataParam("km") Integer km,
			@FormDataParam("precio") Integer precio,
			@FormDataParam("provincia") String provincia,
			@FormDataParam("descripcion") String descripcion,
			@FormDataParam("image") InputStream image,
			@FormDataParam("image") FormDataContentDisposition fileDisposition) {
		UUID uuid = writeAndConvertImage(image);

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_ANUNCIO_QUERY);
			stmt.setString(1, uuid.toString());
			stmt.setInt(2, 0);
			stmt.setString(3, security.getUserPrincipal().getName());
			stmt.setString(4, titulo);
			stmt.setString(5, descripcion);
			stmt.setString(6, marca);
			stmt.setString(7, modelo);
			stmt.setInt(8, km);
			stmt.setInt(9, precio);
			stmt.setString(10, provincia);						
			stmt.executeUpdate();
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
		Anuncio anuncio = new Anuncio();
		anuncio.setFilename(uuid.toString() + ".png");
		anuncio.setImageURL(app.getProperties().get("imgBaseURL")
				+ anuncio.getFilename());

		return anuncio;
	}

	private UUID writeAndConvertImage(InputStream file) {
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		//	image.getScaledInstance(640, 480, Image.SCALE_AREA_AVERAGING);

		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Something has been wrong when reading the file.");
		}
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString() + ".png";
		
		try {
			ImageIO.write(
					image,
					"png",
					
					new File(app.getProperties().get("uploadFolder") + filename));
		} catch (IOException e) {
			throw new InternalServerErrorException(
					"Something has been wrong when converting the file.");
		}

		return uuid;
	}
	
	
}

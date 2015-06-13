package edu.upc.eetac.dsa.dsaqp1415g6.api;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.ws.rs.InternalServerErrorException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


import org.apache.commons.codec.digest.DigestUtils;

import edu.upc.eetac.dsa.dsaqp1415g6.api.model.Roles;
import edu.upc.eetac.dsa.dsaqp1415g6.api.model.User;


@Path("/users")
public class UserResource {
	@Context
	private Application app;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
 
	private final static String GET_USER_BY_USERNAME_QUERY = "select * from users where username=?";
	private final static String INSERT_USER_INTO_USERS = "insert into users values(?, ?, MD5(?), ?, ?)";
	private final static String INSERT_USER_INTO_USER_ROLES = "insert into user_roles values (?, 'registrado')";
	private final static String GET_USER_BY_ID_QUERY= "select * from users where username=?";
	private final static String GET_ROLENAME_BY_ID_QUERY= "select rolename from user_roles where username=?";
	@POST
	@Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
	public User createUser(@FormDataParam("username") String username, 
			@FormDataParam("password") String password,
			@FormDataParam("name") String name,
			@FormDataParam("email") String email,
			@FormDataParam("image") InputStream image,
			@FormDataParam("image") FormDataContentDisposition fileDisposition) {
		
		User user = new User();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmtGetUsername = null;
		PreparedStatement stmtInsertUserIntoUsers = null;
		PreparedStatement stmtInsertUserIntoUserRoles = null;
		try {
			stmtGetUsername = conn.prepareStatement(GET_USER_BY_USERNAME_QUERY);
			
			stmtGetUsername.setString(1, username);
			
			ResultSet rs = stmtGetUsername.executeQuery();
			if (rs.next())
				throw new WebApplicationException(username
						+ " already exists.", Status.CONFLICT);
			rs.close();
			
			conn.setAutoCommit(false);
			 
			stmtInsertUserIntoUsers = conn
					.prepareStatement(INSERT_USER_INTO_USERS);
			stmtInsertUserIntoUserRoles = conn
					.prepareStatement(INSERT_USER_INTO_USER_ROLES);
			UUID uuid = writeAndConvertImage(image);
			user.setFilename(uuid.toString() + ".png");
			stmtInsertUserIntoUsers.setString(1, uuid.toString());
			stmtInsertUserIntoUsers.setString(2, username);
			stmtInsertUserIntoUsers.setString(3, password);
			stmtInsertUserIntoUsers.setString(4, name);
			stmtInsertUserIntoUsers.setString(5, email);
			stmtInsertUserIntoUsers.executeUpdate();
 
			stmtInsertUserIntoUserRoles.setString(1, username);
			stmtInsertUserIntoUserRoles.executeUpdate();
 
			conn.commit();
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
		/*	throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);*/
			
		} finally {
			try {
				if (stmtGetUsername != null)
					stmtGetUsername.close();
				if (stmtInsertUserIntoUsers != null)
					stmtGetUsername.close();
				if (stmtInsertUserIntoUserRoles != null)
					stmtGetUsername.close();
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		
		user.setImageURL(app.getProperties().get("imgBaseURL")
				+ user.getFilename());
		user.setPassword(null);
		return user;
	}
 
	private void validateUser(User user) {
		if (user.getUsername() == null)
			throw new BadRequestException("username cannot be null.");
		if (user.getPassword() == null)
			throw new BadRequestException("password cannot be null.");
		if (user.getName() == null)
			throw new BadRequestException("name cannot be null.");
		if (user.getEmail() == null)
			throw new BadRequestException("email cannot be null.");
	}
 
	@Path("/login")
	@POST
	@Produces(MediaType.ANUNCIOS_API_USER)
	@Consumes(MediaType.ANUNCIOS_API_USER)
	public User login(User user) {
		if (user.getUsername() == null || user.getPassword() == null)
			throw new BadRequestException(
					"username and password cannot be null.");
 
		String pwdDigest = DigestUtils.md5Hex(user.getPassword());
		String storedPwd = getUserFromDatabase(user.getUsername(), true)
				.getPassword();
 
		user.setLoginSuccessful(pwdDigest.equals(storedPwd));
		user.setPassword(null);
		return user;
	}
 
	private User getUserFromDatabase(String username, boolean password) {
		User user = new User();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_USER_BY_USERNAME_QUERY);
			stmt.setString(1, username);
 
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				
				user.setUsername(rs.getString("username"));
				if (password)
					user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
			} else
				throw new NotFoundException(username + " not found.");
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
 
		return user;
	}
	private UUID writeAndConvertImage(InputStream file) {
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
			//image.getScaledInstance(100, 100, Image.SCALE_AREA_AVERAGING);

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
	@GET
	@Path("/{username}")
	@Produces(MediaType.MENSAJES_API_MENSAJE)
	public User getAnuncio(@PathParam("username") String username) {
		User user = new User();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		
		
		try {
			stmt = conn.prepareStatement(GET_USER_BY_ID_QUERY);
			stmt.setString(1, String.valueOf(username));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setFilename(rs.getString("imagen") + ".png");
				user.setImageURL(app.getProperties().get("imgBaseURL")
						+ user.getFilename());
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			
				
			}
			else {
				throw new NotFoundException("No hay usuario con nombre de usuario ="
						+ username);
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
	
		return user;
	}
	@GET
	@Path("/roles/{username}")
	@Produces(MediaType.MENSAJES_API_MENSAJE)
	public Roles getRolename(@PathParam("username") String username) {
		Roles roles  = new Roles();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		
		
		try {
			stmt = conn.prepareStatement(GET_ROLENAME_BY_ID_QUERY);
			stmt.setString(1, String.valueOf(username));
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				roles.setRolename(rs.getString("rolename"));
			
				
			}
			else {
				throw new NotFoundException("No hay rolename con nombre de usuario ="
						+ username);
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
	
		return roles;
	}
}

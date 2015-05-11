package edu.upc.eetac.dsa.dsaqp1415g6.api;



import javax.sql.DataSource;
import javax.ws.rs.Path;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Application;





import edu.upc.eetac.dsa.dsaqp1415g6.api.model.Anuncio;
import edu.upc.eetac.dsa.dsaqp1415g6.api.model.Favorito;
import edu.upc.eetac.dsa.dsaqp1415g6.api.model.FavoritoCollection;

@Path("/favoritos")
public class FavoritoResource {
	@Context
	private Application app;
	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String ListarAnunciosFavoritos = "select * from favoritos where username=? and creation_timestamp < ifnull(?, now()) ORDER BY creation_timestamp desc limit ?"; 
	private String ListarAnunciosFavoritos_last = "select * from favoritos where username=? and creation_timestamp > ? ORDER BY creation_timestamp DESC";
	private String DELETE_FAVORITO_QUERY = "delete from favoritos where idanuncio=?";
	

	
	@GET
	@Produces(MediaType.FAVORITOS_API_FAVORITO_COLLECTION)

	public FavoritoCollection getFavoritos(@QueryParam("length") int length, @QueryParam("before") long before, @QueryParam("after") long after) {
			FavoritoCollection favoritos= new FavoritoCollection();
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
							.prepareStatement(ListarAnunciosFavoritos_last) : conn
							.prepareStatement(ListarAnunciosFavoritos);
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
						    stmt.setString(1, security.getUserPrincipal().getName());
							
						}
					}
				
					ResultSet rs = stmt.executeQuery();
					boolean first = true;
					long oldestTimestamp = 0;
					while (rs.next()) {
						Favorito favorito = new Favorito();
					
						favorito.setUsername(rs.getString("username"));
						favorito.setIdanuncio(rs.getInt("idanuncio"));
						favorito.setLast_modified(rs.getTimestamp("last_modified")
								.getTime());
						favorito.setCreation_timestamp(rs
								.getTimestamp("creation_timestamp").getTime());
						oldestTimestamp = rs.getTimestamp("creation_timestamp").getTime();
						favorito.setLast_modified(oldestTimestamp);
						if (first) {
							first = false;
							favoritos.setNewestTimestamp(favorito.getCreation_timestamp());
						}
						favoritos.addFavorito(favorito);
					}
					favoritos.setOldestTimestamp(oldestTimestamp);
					
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

				return favoritos;
	}
	
	@DELETE
	@Path("/{idanuncio}")
	public void deleteFavorito(@PathParam("idanuncio") String idanuncio) {
	
		validateUser(idanuncio);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_FAVORITO_QUERY);
			stmt.setInt(1, Integer.valueOf(idanuncio));
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("No hay favorito con idanuncio="
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
	    Favorito favorito = getFavorito(idanuncio);
	    String username = favorito.getUsername();
		if (!security.getUserPrincipal().getName()
				.equals(username))
			throw new ForbiddenException(
					"No se le permite modificar este anuncio.");
}

	
	
		
		

		


}

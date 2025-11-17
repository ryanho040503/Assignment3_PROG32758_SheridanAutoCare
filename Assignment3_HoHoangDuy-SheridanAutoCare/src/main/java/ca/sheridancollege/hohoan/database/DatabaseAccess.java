package ca.sheridancollege.hohoan.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.hohoan.beans.User;
import ca.sheridancollege.hohoan.beans.Vehicle;

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	public List<String> getRolesById(Long userId) 
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT sec_role.roleName "
					 + "FROM user_role, sec_role "
					 + "WHERE user_role.roleId = sec_role.roleId "
					 + "AND userId = :userId";
		namedParameters.addValue("userId", userId);
		return jdbc.queryForList(query,namedParameters, String.class);
	}
	
	public User findUserAccount(String email) 
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM sec_user WHERE email = :email"; 
		namedParameters.addValue("email", email);
		try 
		{
			return jdbc.queryForObject(query,namedParameters,new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException erdae)
		{ 
			return null;
		}
	}

	public void addUser(String email, String password)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO sec_user (email, encryptedPassword, enabled) " 
					 + "VALUES (:email, :encryptedPassword, 1)";
;
		namedParameters.addValue("email",email);
		namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
		jdbc.update(query, namedParameters);
	}
	
	
	public void addRole(Long userId, Long roleId)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO user_role (userId, roleId) " 
					 + "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);
		jdbc.update(query, namedParameters);
	}
	
	public void addVehicle(Vehicle vehicle)
	{
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO vehicles "
					 + "(OWNER_NAME, VEHICLE_MODEL, LICENSE_PLATE, SERVICE_DATE, SERVICE_COST, SERVICE_TYPE_ID)"
					 + " VALUES (:ownerName, :vehicleModel, :licensePlate, :serviceDate, :serviceCost, :serviceTypeId)";
		namedParameters.addValue("ownerName", vehicle.getOwner_name());
		namedParameters.addValue("vehicleModel", vehicle.getVehicle_model());
		namedParameters.addValue("licensePlate", vehicle.getLicense_plate());
		namedParameters.addValue("serviceDate", vehicle.getService_date());
		namedParameters.addValue("serviceCost", vehicle.getService_cost());
		namedParameters.addValue("serviceTypeId", vehicle.getService_type_id());
		
//		Using jdbc.update()
		jdbc.update(query, namedParameters);
	}
	
	public void updateVehicleServiceCost(Long vehicleId, Double serviceCost)
	{
	    MapSqlParameterSource namedParameters = new MapSqlParameterSource();

	    String query = "UPDATE vehicles "
	    		     + "SET SERVICE_COST = :serviceCost "
	    		     + "WHERE VEHICLE_ID = :vehicleId";

	    namedParameters.addValue("serviceCost", serviceCost);
	    namedParameters.addValue("vehicleId", vehicleId);

	    jdbc.update(query, namedParameters);
	}
	
	
}

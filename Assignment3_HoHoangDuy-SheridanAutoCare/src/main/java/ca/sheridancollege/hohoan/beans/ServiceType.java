package ca.sheridancollege.hohoan.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ServiceType {

	private Long service_type_id;
	@NonNull
	private String service_name;
	private String description;
	
}

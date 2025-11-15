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
public class User {

	private Long userId;
	@NonNull
	private String email;
	@NonNull
	private String encryptedPassword;
	private boolean enabled;
	
}

package services;

public class TrainerNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public TrainerNotFoundException(String message) {
        super(message);
    }
}

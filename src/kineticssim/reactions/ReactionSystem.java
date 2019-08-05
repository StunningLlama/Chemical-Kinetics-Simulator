package kineticssim.reactions;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/*****
 * Chemical Kinetics Simulator
 * @author Brandon
 * E block
 *****/

//The class that contains the list of reactions.
public class ReactionSystem {
	private Collection<Reaction> reactions;
	
	public ReactionSystem() {
		reactions = new HashSet<Reaction>();
	}
	
	public void addReaction(Reaction r) {
		reactions.add(r);
	}
	
	public void removeReaction(Reaction r) {
		reactions.remove(r);
	}
	
	public Collection<Reaction> getReactions() {
		return reactions;
	}
}

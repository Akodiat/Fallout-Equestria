package common;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import components.InputComp;
import components.PhysicsComp;
import components.TransformationComp;
import entityFramework.IEntityDatabase;
/**
 * 
 * @author Joakim Johansson
 *
 */
public interface IRemoteServer extends Remote{
	
	public void setNewInpComp(InputComp inpComp, String playerLabel) 		throws RemoteException; //TODO Make something better to identify player, perhaps client registry something?
	public void addPlayer(String playerArchString)							throws RemoteException;
	public void registerClient()											throws RemoteException;
	
	public String 							getClientLabel()				throws RemoteException;
	public List<String> 					getOtherPlayerArchetypes()		throws RemoteException;
	public Map<String,TransformationComp> 	getTranspComps() 				throws RemoteException;
	public Map<String, PhysicsComp> 		getPhysComps() 					throws RemoteException;
	public IEntityDatabase 					getDatabase() 					throws RemoteException;
	public String 							test(String s)					throws RemoteException;
	
}	
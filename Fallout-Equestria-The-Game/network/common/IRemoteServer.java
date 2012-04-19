package common;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
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
	
	public void setNewInpComp(InputComp inpComp, int PlayerEntityID) 	throws RemoteException; //TODO Make something better to identify player, perhaps client registry something?
	public void addPlayer(String playerArchString)						throws RemoteException;
	
	public Map<Integer, TransformationComp> getTranspComps() 			throws RemoteException;
	public Map<Integer, PhysicsComp> getPhysComps() 					throws RemoteException;
	public IEntityDatabase getDatabase() 								throws RemoteException;
	public String test(String s)										throws RemoteException;
	
}	
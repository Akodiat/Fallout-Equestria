// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

package server;

public final class RemoteServer_Stub
    extends java.rmi.server.RemoteStub
    implements common.IRemoteServer, java.rmi.Remote
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_addPlayer_0;
    private static java.lang.reflect.Method $method_getDatabase_1;
    private static java.lang.reflect.Method $method_getPhysComps_2;
    private static java.lang.reflect.Method $method_getTranspComps_3;
    private static java.lang.reflect.Method $method_setNewInpComp_4;
    private static java.lang.reflect.Method $method_test_5;
    
    static {
	try {
	    $method_addPlayer_0 = common.IRemoteServer.class.getMethod("addPlayer", new java.lang.Class[] {java.lang.String.class});
	    $method_getDatabase_1 = common.IRemoteServer.class.getMethod("getDatabase", new java.lang.Class[] {});
	    $method_getPhysComps_2 = common.IRemoteServer.class.getMethod("getPhysComps", new java.lang.Class[] {});
	    $method_getTranspComps_3 = common.IRemoteServer.class.getMethod("getTranspComps", new java.lang.Class[] {});
	    $method_setNewInpComp_4 = common.IRemoteServer.class.getMethod("setNewInpComp", new java.lang.Class[] {components.InputComp.class, int.class});
	    $method_test_5 = common.IRemoteServer.class.getMethod("test", new java.lang.Class[] {java.lang.String.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public RemoteServer_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of addPlayer(String)
    public void addPlayer(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_addPlayer_0, new java.lang.Object[] {$param_String_1}, -518118651880743526L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getDatabase()
    public entityFramework.IEntityDatabase getDatabase()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getDatabase_1, null, -9149672743686306073L);
	    return ((entityFramework.IEntityDatabase) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getPhysComps()
    public java.util.Map getPhysComps()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getPhysComps_2, null, -1898064680281658327L);
	    return ((java.util.Map) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of getTranspComps()
    public java.util.Map getTranspComps()
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_getTranspComps_3, null, 7622408750090971150L);
	    return ((java.util.Map) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of setNewInpComp(InputComp, int)
    public void setNewInpComp(components.InputComp $param_InputComp_1, int $param_int_2)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_setNewInpComp_4, new java.lang.Object[] {$param_InputComp_1, new java.lang.Integer($param_int_2)}, -1047477395031961948L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of test(String)
    public java.lang.String test(java.lang.String $param_String_1)
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_test_5, new java.lang.Object[] {$param_String_1}, 2232163481599348975L);
	    return ((java.lang.String) $result);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\mx\\Downloads\\MlService3\\app\\src\\main\\aidl\\cn\\ml_tech\\mx\\mlservice\\IMlService.aidl
 */
package cn.ml_tech.mx.mlservice;
// Declare any non-default types here with import statements

public interface IMlService extends android.os.IInterface
{
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements cn.ml_tech.mx.mlservice.IMlService
    {
        private static final java.lang.String DESCRIPTOR = "cn.ml_tech.mx.mlservice.IMlService";
        /** Construct the stub at attach it to the interface. */
        public Stub()
        {
            this.attachInterface(this, DESCRIPTOR);
        }
        /**
         * Cast an IBinder object into an cn.ml_tech.mx.mlservice.IMlService interface,
         * generating a proxy if needed.
         */
        public static cn.ml_tech.mx.mlservice.IMlService asInterface(android.os.IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof cn.ml_tech.mx.mlservice.IMlService))) {
                return ((cn.ml_tech.mx.mlservice.IMlService)iin);
            }
            return new cn.ml_tech.mx.mlservice.IMlService.Stub.Proxy(obj);
        }
        @Override public android.os.IBinder asBinder()
        {
            return this;
        }
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
        {
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_addMotorControl:
                {
                    data.enforceInterface(DESCRIPTOR);
                    cn.ml_tech.mx.mlservice.MotorControl _arg0;
                    if ((0!=data.readInt())) {
                        _arg0 = cn.ml_tech.mx.mlservice.MotorControl.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg0 = null;
                    }
                    this.addMotorControl(_arg0);
                    reply.writeNoException();
                    return true;
                }
                case TRANSACTION_checkAuthority:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    boolean _result = this.checkAuthority(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_addDrugInfo:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.lang.String _arg0;
                    _arg0 = data.readString();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    int _arg3;
                    _arg3 = data.readInt();
                    int _arg4;
                    _arg4 = data.readInt();
                    boolean _result = this.addDrugInfo(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    reply.writeInt(((_result)?(1):(0)));
                    return true;
                }
                case TRANSACTION_queryDrugControl:
                {
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<cn.ml_tech.mx.mlservice.DrugControls> _result = this.queryDrugControl();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
        private static class Proxy implements cn.ml_tech.mx.mlservice.IMlService
        {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote)
            {
                mRemote = remote;
            }
            @Override public android.os.IBinder asBinder()
            {
                return mRemote;
            }
            public java.lang.String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }
            /**
             * Demonstrates some basic types that you can use as parameters
             * and return values in AIDL.
             */
            @Override public void addMotorControl(cn.ml_tech.mx.mlservice.MotorControl mControl) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((mControl!=null)) {
                        _data.writeInt(1);
                        mControl.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    mRemote.transact(Stub.TRANSACTION_addMotorControl, _data, _reply, 0);
                    _reply.readException();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
            @Override public boolean checkAuthority(java.lang.String name, java.lang.String password) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(password);
                    mRemote.transact(Stub.TRANSACTION_checkAuthority, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override public boolean addDrugInfo(java.lang.String name, java.lang.String enName, java.lang.String pinYin, int containterId, int factoryId) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                boolean _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    _data.writeString(enName);
                    _data.writeString(pinYin);
                    _data.writeInt(containterId);
                    _data.writeInt(factoryId);
                    mRemote.transact(Stub.TRANSACTION_addDrugInfo, _data, _reply, 0);
                    _reply.readException();
                    _result = (0!=_reply.readInt());
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            @Override public java.util.List<cn.ml_tech.mx.mlservice.DrugControls> queryDrugControl() throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<cn.ml_tech.mx.mlservice.DrugControls> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_queryDrugControl, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(cn.ml_tech.mx.mlservice.DrugControls.CREATOR);
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
        }
        static final int TRANSACTION_addMotorControl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_checkAuthority = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_addDrugInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_queryDrugControl = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
    }
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    public void addMotorControl(cn.ml_tech.mx.mlservice.MotorControl mControl) throws android.os.RemoteException;
    public boolean checkAuthority(java.lang.String name, java.lang.String password) throws android.os.RemoteException;
    public boolean addDrugInfo(java.lang.String name, java.lang.String enName, java.lang.String pinYin, int containterId, int factoryId) throws android.os.RemoteException;
    public java.util.List<cn.ml_tech.mx.mlservice.DrugControls> queryDrugControl() throws android.os.RemoteException;
}

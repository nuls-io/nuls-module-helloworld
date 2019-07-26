package io.nuls;

import io.nuls.core.core.annotation.Autowired;
import io.nuls.core.core.annotation.Component;
import io.nuls.core.core.annotation.Value;
import io.nuls.core.exception.NulsException;
import io.nuls.core.log.Log;
import io.nuls.core.rpc.model.ModuleE;
import io.nuls.core.rpc.modulebootstrap.Module;
import io.nuls.core.rpc.modulebootstrap.RpcModuleState;
import io.nuls.rpctools.AccountTools;
import io.nuls.rpctools.TransactionTools;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-10 20:54
 * @Description: 模块业务实现类
 */
@Component
public class MyModule {

    @Autowired
    AccountTools accountTools;

    @Value("address")
    String address;

    @Value("password")
    String password;


    /**
     * 启动模块
     * 模块启动后，当申明的依赖模块都已经准备就绪将调用此函数
     * @param moduleName
     * @return
     */
    public RpcModuleState startModule(String moduleName){
        //validation account
        try {
            if(accountTools.accountValid(2,address,password)){
                Log.info("account validation success ");
            }else{
                Log.info("account valid fail ");
            }
        } catch (NulsException e) {
            e.printStackTrace();
        }
        return RpcModuleState.Running;
    }

    /**
     * 申明需要依赖的其他模块
     * 验证账户需要依赖账户模块
     * @return
     */
    public Module[] declareDependent() {
        return new Module[]{
                Module.build(ModuleE.AC)
        };
    }

}

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
import io.nuls.rpctools.vo.Account;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-10 20:54
 * @Description: 模块业务实现类
 */
@Component
public class MyModule {

    @Autowired
    AccountTools accountTools;

    @Value("addressPriKey")
    String priKey;

    @Value("password")
    String password;

    @Value("chainId")
    int chainId;


    /**
     * 启动模块
     * 模块启动后，当申明的依赖模块都已经准备就绪将调用此函数
     * @param moduleName
     * @return
     */
    public RpcModuleState startModule(String moduleName){
        // import account by private key
        String address = accountTools.importAddress(chainId,priKey,password);
        // get account info
        Account account = accountTools.getAccountByAddress(chainId,address);
        Log.info("=".repeat(50) + " account info " + "=".repeat(50));
        Log.info("account address : {}",account.getAddress());
        Log.info("account pubKey hex : {}",account.getPubkeyHex());
        Log.info("account alias : {}",account.getAlias());
        Log.info("account encrypted prikey hex : {}",account.getEncryptedPrikeyHex());
        Log.info("=".repeat(100 + " account info ".length()));
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

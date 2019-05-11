package org.polkadot.example;

import com.onehilltech.promises.Promise;
import org.polkadot.api.promise.ApiPromise;
import org.polkadot.direct.IRpcFunction;
import org.polkadot.types.type.Header;

import java.util.concurrent.atomic.AtomicReference;

public class ListenToBlocks {
    public static void main(String[] args) {

        Promise<ApiPromise> ready = ApiPromise.create();

        AtomicReference<IRpcFunction.Unsubscribe> unsubscribe = new AtomicReference<>();

        ready.then(api -> {
            IRpcFunction subscribeNewHead = api.rpc().chain().function("subscribeNewHead");
            Promise<IRpcFunction.Unsubscribe<Promise>> invoke = subscribeNewHead.invoke(
                    (IRpcFunction.SubscribeCallback<Header>) (Header header) ->
                    {
                        //System.out.println("Chain is at block: " + JSON.toJSONString(header));
                        System.out.println("Chain is at block: " + header.getBlockNumber());
                    });
            System.out.println("invoke " + invoke.getStatus() + ": " + invoke.getName());
            return invoke;
        }).then((IRpcFunction.Unsubscribe<Promise> result) -> {
            unsubscribe.set(result);
            System.out.println(" set unsubscribe " + unsubscribe);
            return null;
        })._catch((err) -> {
            err.printStackTrace();
            return Promise.value(err);
        });


        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("do unsubscribe = " + unsubscribe);

        if (unsubscribe.get() != null) {
            unsubscribe.get().unsubscribe();
        }
    }
}

package org.polkadot.types.primitive;

import org.polkadot.types.codec.UInt;

/**
 * @name U64
 * @description An 64-bit unsigned integer
 */
public class U64 extends UInt {
    //  constructor (value?: AnyNumber) {
    public U64(Object value) {
        super(value, 64);
    }
}

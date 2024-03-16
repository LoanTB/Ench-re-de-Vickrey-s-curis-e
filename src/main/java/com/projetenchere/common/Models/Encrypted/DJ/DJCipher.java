package com.projetenchere.common.Models.Encrypted.DJ;

import jdk.jshell.spi.ExecutionControl;

import javax.crypto.*;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;

public class DJCipher extends CipherSpi {

    private Key key;
    private int opmode;
    private AlgorithmParameters params;
    private SecureRandom random;

    @Override
    protected void engineSetMode(String mode) throws NoSuchAlgorithmException {
    }

    @Override
    protected void engineSetPadding(String padding) throws NoSuchPaddingException {
    }

    @Override
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override
    protected int engineGetOutputSize(int inputLen) {
        return 0;
    }

    @Override
    protected byte[] engineGetIV() {
        return new byte[0];
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override
    protected void engineInit(int opmode, Key key, SecureRandom random) throws InvalidKeyException {

    }

    @Override
    protected void engineInit(int opmode, Key key, AlgorithmParameterSpec params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {

    }

    @Override
    protected void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.opmode = opmode;
        this.key = key;
        this.params = params;
        this.random = random;
    }

    @Override
    protected byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        return new byte[0];
    }

    @Override
    protected int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException {
        return 0;
    }

    @Override
    protected byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) throws IllegalBlockSizeException, BadPaddingException {
        return input;
    }

    @Override
    protected int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        return 0;
    }
}

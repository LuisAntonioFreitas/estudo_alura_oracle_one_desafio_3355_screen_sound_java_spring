package net.lanet.screensound.enums;

public enum SimNao {
    SIM,
    NAO;

    public static SimNao fromString(String input) {
        SimNao result = null;
        switch (input.trim().toUpperCase()) {
            case "SIM": result = SIM; break;
            case "S": result = SIM; break;
            case "YES": result = SIM; break;
            case "Y": result = SIM; break;
            case "NAO": result = NAO; break;
            case "NÃO": result = NAO; break;
            case "N": result = NAO; break;
            case "NO" : result = NAO; break;
        }
        return result;
    }
}

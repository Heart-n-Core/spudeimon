package ukma.ipz.fight;

public enum Types {
    FI, FHN, FPVN, FSNST, FPRN, FOZ, KMBS, FEN, OTHER;

    public static float attackEfficiency(Types attacker, Types defender) {
        float res = 1f;
        switch (attacker) {
            case FI: {
                if (defender == FEN || defender == FPVN) res = 2;
                if (defender == FHN || defender == FPRN) res = 0.5f;
                break;
            }
            case FHN: {
                if (defender == FI || defender == KMBS) res = 2;
                if (defender == FSNST || defender == FOZ) res = 0.5f;
                break;
            }
            case FPVN: {
                if (defender == KMBS || defender == FSNST) res = 2;
                if (defender == FI || defender == FSNST) res = 0.5f;
                break;
            }
            case FSNST: {
                if (defender == FPVN || defender == FPRN) res = 2;
                if (defender == FPVN || defender == FEN) res = 0.5f;
                break;
            }
            case FPRN: {
                if (defender == FI || defender == FHN) res = 2;
                if (defender == KMBS || defender == FEN) res = 0.5f;
                break;
            }

            case FOZ: {
                if (defender == FHN || defender == FEN) res = 2;
                if (defender == KMBS || defender == FOZ) res = 0.5f;
                break;
            }

            case KMBS: {
                if (defender == FPRN || defender == FOZ) res = 2;
                if (defender == FPVN || defender == FHN) res = 0.5f;
                break;
            }

            case FEN: {
                if (defender == FPRN || defender == FSNST) res = 2;
                if (defender == FI || defender == FOZ) res = 0.5f;
                break;
            }

        }
        if (attacker == OTHER || defender == OTHER) res = 2;
        return res;
    }
}


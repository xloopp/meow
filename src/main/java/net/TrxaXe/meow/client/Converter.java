package net.TrxaXe.meow.client;
import java.util.Random;

public class Converter {
    // 可爱的后缀词库
    private static final String[] NEKO_SUFFIXES = {
            "喵~", "喵！", "喵...", "~喵", "=^･ω･^=", " (ฅ´ω`ฅ)", " (≧▽≦)",
            " nya~", " 呜喵...", " >_<", " (｡･ω･｡)", " (=｀ω´=)", " 喵呜~"
    };

    // 替换词库（普通词 -> 猫娘词）
    private static final String[][] WORD_REPLACEMENTS = {
            {"我", "人家"}, {"你", "主人"}, {"他", "那个人"}, {"她", "那个女孩子"},
            {"的", "的喵"}, {"了", "了的喵"}, {"呢", "喵"}, {"啊", "喵"},
            {"吃饭", "吃小鱼干"}, {"喜欢", "最最最喜欢"}, {"好", "喵好"}, {"开心", "敲开心"},
            {"早上好", "喵早安"}, {"晚安", "晚安安喵"}, {"谢谢", "阿里嘎多喵"}, {"对不起", "果咩纳塞喵"}
    };

    private static final Random random = new Random();

    /**
     * 将普通文本转换为猫娘化文本
     * @param text 原始文本
     * @return 猫娘化后的文本
     */
    public static String Catgirl(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        // 1. 替换特定词汇
        for (String[] replacement : WORD_REPLACEMENTS) {
            text = text.replace(replacement[0], replacement[1]);
        }

        // 2. 随机添加可爱后缀（约90%的概率）
        if (random.nextInt(100) < 90) {
            int suffixIndex = random.nextInt(NEKO_SUFFIXES.length);
            text += NEKO_SUFFIXES[suffixIndex];
        }

        // 3. 随机将句号替换为猫娘符号（约80%的概率）
        if (random.nextInt(100) < 80) {
            text = text.replace("。", "喵~");
            text = text.replace(".", "喵~");
        }

        // 4. 随机将"吗"替换为"喵"（约70%的概率）
        if (random.nextInt(100) < 70) {
            text = text.replace("吗", "喵");
            text = text.replace("么", "喵");
        }

        return text;
    }
}
package mix.baidu.model;

import java.util.List;

public record NormRes(List<NormARes> words_result, Integer words_result_num, Long log_id) {
}

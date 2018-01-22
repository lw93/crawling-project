import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import com.liuwei.db.DBHelper;
import com.liuwei.pojo.NewsPOJO;
import com.liuwei.util.PropertiesUtil;

/**
 * @Project: news-crawling1
 * @Class: DBTest.java
 * @Description:
 * @Date: 2017年11月9日
 * @author liuwei5
 */
public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(PropertiesUtil.getNewsType("T1348649776727"));
		NewsPOJO newsPOJO = new NewsPOJO();
		newsPOJO.setTitle("闺蜜俩网购玻尿酸互“扎”一针下去眼睛看不见了");
		String bodyString = "<p>　　<strong>楚天都市报11月09日讯 <\\/strong>少年胸部“发育”到了B罩杯，吓得妈妈以为他是双性人。男孩胸部为何会发育？可能是炸鸡吃太多。8日，医院乳腺外科开科专家提醒：家长要警惕，青少年也会得乳腺疾病。<\\/p><p>　　家住硚口区的15岁男生乐乐（化名）身高167cm，体重120斤，身体壮实但不胖。一直有个难言之隐令他十分苦恼尴尬，他的胸部“发育”到了B罩杯。"
				+ "妈妈周女士一看孩子的胸部比同年龄的女孩子还大，加上声音也有点细声细气，怀疑他是双性人。近日，周女士赶紧带乐乐来到医院乳腺外科检查。<\\/p><p>　　“医生，我儿子胸部比同龄的女孩都大，大的不正常。”周女士一脸愁容地向乳腺外科主任叶春梅说明情况，乐乐一直低头不语。在医生的要求下，乐乐掀开了衣服。医生发现，乐乐的胸部差不多有B罩杯大，而且乳头和乳晕也有发育增大。<\\/p><p>　"
				+ "据乐乐妈妈介绍，乐乐也曾是个小胖墩，从小特别爱吃炸鸡、牛排等高热量的食物，出门吃饭也常常是洋快餐配碳酸饮料，13岁时，孩子的体重长到130斤，比同龄人超重不少，于是全家人开始控制乐乐的饮食，让他“管住了嘴，迈开了腿”，两年来瘦了10斤。<\\/p><p>　　但乐乐的胸部却一直没能“瘦下来”。原本以为是长胖后脂肪堆积到了胸部。没想到，胸部却不受控制，越长越大，甚至影响到了生活。冬天穿厚点可以遮掩，但夏天完全不敢穿紧身的衣服，"
				+ "同学朋友们有时候还拿这个开玩笑，这让乐乐十分自卑，走路总是含着胸，从不敢当众脱衣服。<\\/p><p>　　"
				+ "经过检查，叶春梅主任认为，乐乐为原发性男性乳房发育，病因为内分泌激素失衡导致胸部增大，属于乳腺内的雌激素敏感性相对增高，过度刺激所致。这可能与乐乐从小的饮食习惯有关。<\\/p><p>　　医生解释，正常情况下，男生和女生在出生的时候都具备乳房组织，但乳房组织要进一步发育，需要更多的雌激素。而进入青春期的女性分泌以雌激素为主，所以乳房组织也会慢慢发育；男生在成长过程中分泌的雌激素极低，所以男生的乳房组织就不会继续发育。<\\/p><p>　　"
				+ "但是，乐乐的身体没有按照上述规律发展。他的雌激素、雄激素比例失调，雌激素的占比过高，乳腺就有可能发育起来。所以，乐乐的胸部越来越大。最有效的治疗办法就是采用微创手术切除多余腺体，术后不会有后遗症。<\\/p><p>　　乳腺外科医生介绍，目前青少年男性乳腺发育症逐年增多，每年的寒暑假都会有到医院就诊的学生。这个病症的发展不仅会让男孩产生自卑感影响体形，也会为触发乳腺癌埋下隐患，所以一旦出现这个症状，一定不要讳疾忌医，要及时就诊。<\\/p><p>　　"
				+ "武汉市卫计委妇幼处处长黄涛表示，该院乳腺外科的成立，将进一步巩固全市妇女“两癌”防治体系，为广大妇女提供更优质的乳腺疾病预防、保健与医疗服务，促进区域内妇女健康水平的不断提升。<\\/p><p>原标题：尴尬！15岁少年因炸鸡吃太多，胸部长到了B罩杯<\\/p>";
		newsPOJO.setBody(bodyString);
		newsPOJO.setCreateTime(new Timestamp(System.currentTimeMillis()));
		newsPOJO.setDocId("D2KK7MP000367V0V");
		newsPOJO.setEditor("姬雪莹_NN6784");
		newsPOJO.setImgUrl("http://cms-bucket.nosdn.127.net/0789a1ac11e3492fbcbaddfec65b075220171110091715.jpeg");
		newsPOJO.setLocalImg("sadadsfcasfas".getBytes());
		newsPOJO.setNewsId("T1368497029546");
		newsPOJO.setNewsType("历史");
		newsPOJO.setNewsResouce("荆楚网-楚天都市报");
		newsPOJO.setProductTime(Timestamp.valueOf("2017-11-10 09:16:00"));
		newsPOJO.setRelativeKey("乳腺,外科,炸鸡");
		newsPOJO.setShortCentent("楚天都市报11月09日讯少年胸部“发育”到了B罩杯，吓得妈妈以为他是双性人。男孩胸部为何会发育？可能是炸鸡吃太多。8日，医院乳腺外科开科专家提醒：家长要警惕，青");
		Vector< NewsPOJO> vector = new Vector<NewsPOJO>();
		vector.add(newsPOJO);
		NewsPOJO newsPOJO1 = new NewsPOJO();
		newsPOJO1.setTitle("闺蜜俩网购玻尿酸互“扎”一针下去眼睛看不见了");
		String bodyString1 = "<p>　　<strong>楚天都市报11月09日讯 <\\/strong>少年胸部“发育”到了B罩杯，吓得妈妈以为他是双性人。男孩胸部为何会发育？可能是炸鸡吃太多。8日，医院乳腺外科开科专家提醒：家长要警惕，青少年也会得乳腺疾病。<\\/p><p>　　家住硚口区的15岁男生乐乐（化名）身高167cm，体重120斤，身体壮实但不胖。一直有个难言之隐令他十分苦恼尴尬，他的胸部“发育”到了B罩杯。"
				+ "妈妈周女士一看孩子的胸部比同年龄的女孩子还大，加上声音也有点细声细气，怀疑他是双性人。近日，周女士赶紧带乐乐来到医院乳腺外科检查。<\\/p><p>　　“医生，我儿子胸部比同龄的女孩都大，大的不正常。”周女士一脸愁容地向乳腺外科主任叶春梅说明情况，乐乐一直低头不语。在医生的要求下，乐乐掀开了衣服。医生发现，乐乐的胸部差不多有B罩杯大，而且乳头和乳晕也有发育增大。<\\/p><p>　"
				+ "据乐乐妈妈介绍，乐乐也曾是个小胖墩，从小特别爱吃炸鸡、牛排等高热量的食物，出门吃饭也常常是洋快餐配碳酸饮料，13岁时，孩子的体重长到130斤，比同龄人超重不少，于是全家人开始控制乐乐的饮食，让他“管住了嘴，迈开了腿”，两年来瘦了10斤。<\\/p><p>　　但乐乐的胸部却一直没能“瘦下来”。原本以为是长胖后脂肪堆积到了胸部。没想到，胸部却不受控制，越长越大，甚至影响到了生活。冬天穿厚点可以遮掩，但夏天完全不敢穿紧身的衣服，"
				+ "同学朋友们有时候还拿这个开玩笑，这让乐乐十分自卑，走路总是含着胸，从不敢当众脱衣服。<\\/p><p>　　"
				+ "经过检查，叶春梅主任认为，乐乐为原发性男性乳房发育，病因为内分泌激素失衡导致胸部增大，属于乳腺内的雌激素敏感性相对增高，过度刺激所致。这可能与乐乐从小的饮食习惯有关。<\\/p><p>　　医生解释，正常情况下，男生和女生在出生的时候都具备乳房组织，但乳房组织要进一步发育，需要更多的雌激素。而进入青春期的女性分泌以雌激素为主，所以乳房组织也会慢慢发育；男生在成长过程中分泌的雌激素极低，所以男生的乳房组织就不会继续发育。<\\/p><p>　　"
				+ "但是，乐乐的身体没有按照上述规律发展。他的雌激素、雄激素比例失调，雌激素的占比过高，乳腺就有可能发育起来。所以，乐乐的胸部越来越大。最有效的治疗办法就是采用微创手术切除多余腺体，术后不会有后遗症。<\\/p><p>　　乳腺外科医生介绍，目前青少年男性乳腺发育症逐年增多，每年的寒暑假都会有到医院就诊的学生。这个病症的发展不仅会让男孩产生自卑感影响体形，也会为触发乳腺癌埋下隐患，所以一旦出现这个症状，一定不要讳疾忌医，要及时就诊。<\\/p><p>　　"
				+ "武汉市卫计委妇幼处处长黄涛表示，该院乳腺外科的成立，将进一步巩固全市妇女“两癌”防治体系，为广大妇女提供更优质的乳腺疾病预防、保健与医疗服务，促进区域内妇女健康水平的不断提升。<\\/p><p>原标题：尴尬！15岁少年因炸鸡吃太多，胸部长到了B罩杯<\\/p>";
		newsPOJO1.setBody(bodyString1);
		newsPOJO1.setCreateTime(new Timestamp(System.currentTimeMillis()));
		newsPOJO1.setDocId("D2KK7MP000367V0N");
		newsPOJO1.setEditor("姬雪莹_NN6784");
		newsPOJO1.setImgUrl("http://cms-bucket.nosdn.127.net/0789a1ac11e3492fbcbaddfec65b075220171110091715.jpeg");
		newsPOJO1.setLocalImg("sadadsfcasfas".getBytes());
		newsPOJO1.setNewsId("T1348649776727");
		newsPOJO1.setNewsType("数码");
		newsPOJO1.setNewsResouce("荆楚网-楚天都市报");
		newsPOJO1.setProductTime(Timestamp.valueOf("2017-11-10 09:16:00"));
		newsPOJO1.setRelativeKey("乳腺,外科,炸鸡");
		newsPOJO1.setShortCentent("楚天都市报11月09日讯少年胸部“发育”到了B罩杯，吓得妈妈以为他是双性人。男孩胸部为何会发育？可能是炸鸡吃太多。8日，医院乳腺外科开科专家提醒：家长要警惕，青");
		
		vector.add(newsPOJO1);
		System.out.println(vector.size());
		DBHelper dbHelper = new DBHelper();
//		dbHelper.InsertToDB(vector);
		Vector<NewsPOJO> vector2 = new Vector<NewsPOJO>();
		vector2.add(newsPOJO1);
		dbHelper.InsertToDB(vector);
		//System.out.println(dbHelper.getDBNewsCount());
		//System.out.println(new Timestamp(System.currentTimeMillis()));
	}

}

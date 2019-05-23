package chuck.com.challenge.activities.nameReplaceActivity

import chuck.com.challenge.Models.AsyncJokeRetriever
import chuck.com.challenge.Presenters.ReplaceNamePresenter
import chuck.com.challenge.helpers.RegexHelper
import chuck.com.challenge.helpers.ResourceHelper
import chuck.com.challenge.helpers.UIHelper
import dagger.Module
import dagger.Provides

@Module
class ReplaceNameFragmentModule {

    @Provides
    fun providePresenter(fragment: ReplaceNameFragment, asyncJokeRetriever: AsyncJokeRetriever, resourceHelper: ResourceHelper, uiHelper: UIHelper, regexHelper: RegexHelper) = ReplaceNamePresenter(fragment, asyncJokeRetriever, uiHelper, resourceHelper, regexHelper)

    @Provides
    fun getRegexHelper(uiHelper: UIHelper) = RegexHelper(uiHelper)
}
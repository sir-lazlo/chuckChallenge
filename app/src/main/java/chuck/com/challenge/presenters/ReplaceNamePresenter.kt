package chuck.com.challenge.presenters

import android.graphics.Typeface
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.StyleSpan

import chuck.com.challenge.R
import chuck.com.challenge.contracts.replaceName.ReplaceNameFragmentContract
import chuck.com.challenge.data.repositories.JokesRepository
import chuck.com.challenge.helpers.ResourceHelper
import chuck.com.challenge.helpers.UIHelper

private const val SPACE = " "

class ReplaceNamePresenter(private val jokesRepository: JokesRepository, private val uiHelper: UIHelper, private val resourceHelper: ResourceHelper) : BasePresenter<ReplaceNameFragmentContract.View>(), TextWatcher {

    fun fetchNameReplaceJoke(fullName: String) {
        if (fullName.contains(SPACE)) {
            val splitNames = fullName.split(SPACE)
            val firstName = splitNames.first()
            val lastName = splitNames.last()

            addDisposable(jokesRepository.getNamedReplaceJoke(firstName, lastName).subscribe({
                val jokeTitle = String.format(
                        resourceHelper.getString(R.string.generic_dialog_title),
                        it.id.toString())

                val titleSpan = SpannableString(jokeTitle)
                titleSpan.setSpan(StyleSpan(Typeface.BOLD), jokeTitle.length - it.id.toString().length, jokeTitle.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

                view?.onJokeLoaded(titleSpan,
                        uiHelper.convertStringFromHtml(it.value))
            }, {
                it.message?.let { message ->
                    view?.onError(message)
                }
            }))
        } else {
            view?.showInvalidNameError()
        }
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val isValid = s.contains(SPACE)
        setSubmitButtonStatus(isValid)
        setEditTextErrorStatus(isValid)
    }

    override fun afterTextChanged(s: Editable) {
    }

    private fun setSubmitButtonStatus(stringIsValid: Boolean) {
        view?.onSubmitButtonBackgroundChange(if (stringIsValid)
            R.drawable.button
        else
            R.drawable.button_faded)
    }

    private fun setEditTextErrorStatus(stringIsValid: Boolean) {
        view?.onRemoveTextInputLayoutErrorState(stringIsValid)
    }
}
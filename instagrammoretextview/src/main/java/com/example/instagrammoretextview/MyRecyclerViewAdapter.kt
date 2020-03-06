package com.example.instagrammoretextview

import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.*

class MyRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
    , ReadMoreTextView.IReadMoreTextViewStatusListener {

    override var expandedViewPositionSet: HashSet<Int> = HashSet()

    override fun addExpandedView(position: Int) {
        expandedViewPositionSet.add(position)
    }

    override fun isExpandedView(position: Int): Boolean {
        return expandedViewPositionSet.contains(position).apply {
            Log.d("jhlProductDetailActivityee", "isExpandedView : $this")
        }
    }

    inner class MyViewHolderUI : AnkoComponent<ViewGroup> {
        lateinit var readMoreTextView: ReadMoreTextView

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                readMoreTextView = readMoreTextView {

                }.lparams {
                    width = matchParent
                    height = wrapContent
                    padding = dip(40)
                }
            }
        }
    }

    inner class MyViewHolder(
        private var ui: MyViewHolderUI,
        view: LinearLayout,
        var listener: ReadMoreTextView.IReadMoreTextViewStatusListener
    ) : RecyclerView.ViewHolder(view) {

        fun bind(position: Int) {
            val temp = when (position % 9) {
                0 -> "gogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogogo"
                1 -> "\n\n해해"
                2 -> "고 의원은 굳이 서울에서 1석을 줄인다면 지난 2016년 총선에서 분구된 강남 선거구를 통합하는 것이 합리적인데 이런 기본적인 상식조차 지키지 못했다고 했다. 노원을이 지역구인 우원식 의원은 입장문을 통해 \\\"획정위가 강남구 선거구를 줄이지 않고 노원구 선거구를 줄이는 결정을 한 것은 정치적 유불리를 따라 불공정하고 불합리한 판단을 내린 것이라고 했다.미래통합당 후보로 노원병에 출마 예정인 이준석 최고위원도 페이스북 글에서 노원 갑·을·병이 갑·을로 개편되면 '을' 지역이 둘러 갈라져 기존 '갑'과 '병'으로 붙는 것이라며 선거운동을 해야 하는 대상이 1.5배로 늘어나 비상이라고 했다."
                3 -> "고 의원은 굳이 서울에서 1석을 줄인다면 지난 2016년 총선에서 분구된 강남 선거구를 통합하는 것이 합리적인데 이런 기본적인 상식조차 지키지 못했다고 했다. 노원을\n이 지역구인 우원식 의원은 입장문을 통해 \\\"획정위가 강남구 선거구를 줄이지 않고 노원구 선거구를 줄이는 결정을 한 것은 정치적 유불리를 따라 불공정하고 불합리한 판단을 내린 것이라고 했다.미래통합당 후보로 노원병에 출마 예정인 이준석 최고위원도 페이스북 글에서 노원 갑·을·병이 갑·을로 개편되면 '을' 지역이 둘러 갈라져 기존 '갑'과 '병'으로 붙는 것이라며 선거운동을 해야 하는 대상이 1.5배로 늘어나 비상이라고 했다."
                4 -> "고 의원은 굳이 서울에서 1석을 줄인다면 지난 2016년 총선에서 분구된 강남 선거구를 통합하는 것이 합리적인데 이런 기본적인 상식조차 지키지 못했다고 했다. 노원을\n"
                5 -> "고 의원은 굳이 서울에서 1석을 줄인다면 지난 2016년 총선에서 분구된 강남 선거구를 통합하는 것이 합리적인데 이런 기본적인 상식조차 지키지 못했다고 했다. 노원을"
                6 -> "고 의원은 굳이\n 서울에서 1석을 줄인다면 지난 2016년 총선에서 분구된 강남 선거구를 통합하는 것이 합리적인데 이런 기본적인 상식조차 지키지 못했다\"고 했다. 노원을이 지역구인 우원식 의원은 입장문을 통해 \"획정위가 강남구 선거구를 줄이지 않고 노원구 선거구를 줄이는 결정을 한 것은 정치적 유불리를 따라 불공정하고 불합리한 판단을 내린 것이라고 했다.미래통합당 후보로 노원병에 출마 예정인 이준석 최고위원도 페이스북 글에서 노원 갑·을·병이 갑·을로 개편되면 '을' 지역이 둘러 갈라져 기존 '갑'과 '병'으로 붙는 것이라며 선거운동을 해야 하는 대상이 1.5배로 늘어나 비상이라고 했다."
                else -> "언론들도 혹독한 비판 기사를 쏟아내고 있다."
            }
            with(ui.readMoreTextView) {
                this.onExpandedClickCallbacks = {
                    listener.addExpandedView(position)
                }
                this.isExpandedStatus = listener.isExpandedView(position)
                this.position = position
                this.post {
                    Log.d("jhlee", "setReadMoreText $isExpandedStatus , $position")
                    this.setReadMoreText(temp)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val ui = MyViewHolderUI()
        val view = ui.createView(AnkoContext.create(parent.context, parent))
        return MyViewHolder(ui, view, this)
    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyViewHolder
        holder.bind(position)
    }
}
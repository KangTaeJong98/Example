package com.taetae98.databinding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.taetae98.databinding.ActivityMainNavigationDirections
import com.taetae98.databinding.base.BaseAdapter
import com.taetae98.databinding.base.BaseHolder
import com.taetae98.databinding.data.Movie
import com.taetae98.databinding.databinding.HolderMovieBinding

class MovieAdapter : BaseAdapter<Movie>(MovieItemCallback()) {
    init {
        setHasStableIds(true)
        submitList(mutableListOf(
            Movie(0L, "https://github.com/KangTaeJong98/Example/blob/main/Android/Databinding/image/friends.jpg?raw=true", "Friends", "《프렌즈》(영어: Friends)는 미국 NBC에서 방송된 시트콤이다. 미국 뉴욕시 맨해튼의 그리니치 빌리지에 사는 2-30대의 세 명의 남자와 세 명의 여자의 생활을 그린 시트콤이다. 데이비드 크레인, 마타 코프먼이 제작하였으며, 미국에서는 1994년 9월 22일 방송이 시작되어 2004년 5월 6일 시즌 10을 마지막으로 종영되었다.\n" +
                    "\n" +
                    "미국 현지에서 방영 기간 내내 최고의 인기를 끌었으며, 전 세계적으로 가장 큰 성공을 거둔 시트콤이다. 프렌즈는 시즌 내내 각 주인공들의 헤어스타일, 패션, 각 에피소드의 소품 등 모든 것이 화제를 모았고 특히 레이첼의 헤어 스타일은 미국 여성들에게 최고의 인기를 끌었다. 사실 프렌즈는 시즌7이 마지막이었으나 후속작으로 내세울 만한 작품이 없던 NBC가 시즌10까지 연장키로 하고 주인공들의 출연료를 각 100만달러씩 지급했다. 이는 이 시트콤의 대단한 인기를 증명하는 사건이었다. 에미상 “최고의 코미디 시리즈” 부문을 포함하여 다양한 상을 받았다.\n" +
                    "\n" +
                    "특히 마지막방송 때는 미 전역에서 야외 전광판으로도 방송이 돼 최고의 시트콤이라는 명성에 걸맞은 마무리를 보여줬다.\n" +
                    "\n" +
                    "대한민국에는 케이블 채널인 온스타일에서 2006년 2월부터 방영하고 있으며, 그 전까지는 동아TV에서 방영을 했었다. 동아TV에서 방영 당시의 제목은 \"프렌드\"였다."),
            Movie(1L, "https://github.com/KangTaeJong98/Example/blob/main/Android/Databinding/image/lacasadepapel.jpeg?raw=true", "종이의 집", "넷플릭스에서 스트리밍 중인 스페인 드라마. 넷플릭스 드라마 전체를 통틀어 2위에 오른 기록이 있는 인기작이다. 2020년 4월 현재 파트(시즌)4까지 나왔다.[4]\n" +
                    "\n" +
                    "장르는 범죄 스릴러로, 교수라고 불리는 한 천재가 8명의 범죄자들을 모아 금고를 터는 이야기다. 시즌 1, 2에서는 스페인 조폐국을 터는 내용으로 마무리 되고, 시즌 3, 4는 스페인 중앙은행 지하에 보관되어 있는 금을 터는 내용으로 아직 마무리되지 않아 시즌 5로 이어질 예정이다.\n" +
                    "\n" +
                    "훔치려고 하는 돈의 액수 자체가 조 단위로 굉장히 규모가 크며, 특이한 점은 범죄를 계획하고 지시하는 교수가 범죄현장 밖에 있으면서 내부에 있는 조직원들을 통제하여 지능적으로 범죄를 진행한다는 점이다. 시즌 내내 경찰과 교수 사이의 마치 게임을 하는 듯한 머리싸움의 묘미를 느낄 수 있으며 기발한 방법을 동원하는 범죄수법에 앞으로 또 어떤 절묘한 방법들이 나올지 더욱 기대하게 된다.\n" +
                    "\n" +
                    "“종이의 집”은 스페인어 원제 “La Casa de Papel”의 번역인데, 영제는 이와 의미가 다른 “Money Heist.” 단순히 금전강도라는 뜻이다."),
            Movie(2L, "https://github.com/KangTaeJong98/Example/blob/main/Android/Databinding/image/queensgambit.jpg?raw=true", "Queen's Gambit", "《퀸스 갬빗》(영어: The Queen's Gambit)은 넷플릭스에서 2020년 10월부터 방영된 미국의 드라마이다. 월터 테비스의 동명 소설을 원작으로 스콧 프랭크와 앨런 스콧이 기획하였다. 애니아 테일러조이, 빌 캠프, 모지스 잉그럼, 토머스 브로디생스터, 해리 멜링, 리베카 루트가 출연한다."),
            Movie(3L, "https://github.com/KangTaeJong98/Example/blob/main/Android/Databinding/image/snowpiercer.png?raw=true", "설국열차", "《설국열차》(영어: Snowpiercer)는 TBS와 넷플릭스(미국, 중국 외 지역)에서 2020년 5월부터 방영된 미국의 디스토피아 스릴러 드라마이다. 봉준호 감독의 영화 《설국열차》와 그 원작인 만화 《설국열차》를 바탕으로 하며, 원작의 설정을 기반으로 펼쳐지는 새로운 이야기이다. 원작 영화의 봉준호 감독과 박찬욱, 이태호, 최두호 프로듀서, 스콧 데릭슨, 조시 프리드먼 등이 제작에 참여한다. 배우로는 제니퍼 코널리, 더비드 디그스, 미키 섬너, 수전 박, 애널리스 배소, 리나 홀 등이 출연한다. 미국에서는 TBS 채널에서, 미국 외 국가에서는 넷플릭스에서 공개된다.[1]\n" +
                    "\n" +
                    "미국에서는 2020년 5월 17일부터 TBS를 통해, 대한민국에서는 2020년 5월 25일부터 넷플릭스를 통해 방영되었다."),
            Movie(4L, "https://github.com/KangTaeJong98/Example/blob/main/Android/Databinding/image/theplatform.jpg?raw=true", "The Platform" ,"《더 플랫폼》(스페인어: El Hoyo)은 2019년 공개된 스페인의 SF, 스릴러 영화이다."),
            Movie(5L, "https://github.com/KangTaeJong98/Example/blob/main/Android/Databinding/image/thefamilyman.jpg?raw=true", "패밀리 맨" ,"《패밀리 맨》(영어: The Family Man)은 2000년 개봉된 미국의 로맨틱 드라메디 영화이다.")
        ))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<out ViewDataBinding, Movie> {
        HolderMovieBinding.bind()
        return MovieHolder(HolderMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    inner class MovieHolder(binding: HolderMovieBinding) : BaseHolder<HolderMovieBinding, Movie>(binding) {
        init {
            binding.setOnClick {
                it.findNavController().navigate(ActivityMainNavigationDirections.actionGlobalInformationFragment(element))
            }
        }

        override fun bind(element: Movie) {
            super.bind(element)
            binding.movie = element
        }
    }

    class MovieItemCallback() : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }
    }
}
/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.iss.view.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.iss.R;
import com.iss.view.pulltorefresh.internal.LoadingLayout;
import com.iss.view.pulltorefresh.viewpager.PagerAdapter;
import com.iss.view.pulltorefresh.viewpager.VerticalViewPager;


public class PullToRefreshViewPager extends PullToRefreshBase<VerticalViewPager> {

	public PullToRefreshViewPager(Context context) {
		super(context);
	}

	public PullToRefreshViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public final Orientation getPullToRefreshScrollDirection() {
		return Orientation.VERTICAL;
	}

	@Override
	protected VerticalViewPager createRefreshableView(Context context, AttributeSet attrs) {
		VerticalViewPager viewPager = new VerticalViewPager(context, attrs);
		viewPager.setId(R.id.viewpager);
		return viewPager;
	}

	@Override
	protected boolean isReadyForPullStart() {
		VerticalViewPager refreshableView = getRefreshableView();

		PagerAdapter adapter = refreshableView.getAdapter();
		if (null != adapter) {
			return refreshableView.getCurrentItem() == 0;
		}

		return false;
	}

	@Override
	protected boolean isReadyForPullEnd() {
		VerticalViewPager refreshableView = getRefreshableView();

		PagerAdapter adapter = refreshableView.getAdapter();
		if (null != adapter) {
			return refreshableView.getCurrentItem() == adapter.getCount() - 1;
		}
		
		return false;
	}
	
	/**
	 * 获取刷新的header
	 * @return
	 */
	public LoadingLayout getHeadLayout(){
		return this.getHeaderLayout();
	}
	
	/**
	 * 获取刷新的footer
	 * @return
	 */
	public LoadingLayout getFootLayout(){
		return this.getFooterLayout();
	}
}

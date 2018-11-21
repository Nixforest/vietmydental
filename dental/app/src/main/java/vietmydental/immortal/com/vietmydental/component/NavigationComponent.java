package vietmydental.immortal.com.vietmydental.component;

import android.widget.Toast;

import butterknife.ButterKnife;
import vietmydental.immortal.com.gate.utils.DomainConst;
import vietmydental.immortal.com.vietmydental.utils.BaseModel;
import vietmydental.immortal.com.gate.g00.view.G00HomeActivity;

public class NavigationComponent extends BaseNavigationComponent {
    public NavigationComponent(BaseActivity activity) {
//        if (!(activity instanceof G00HomeActivity)) {
//            return;
//        }
        ButterKnife.bind(this, activity.findViewById(android.R.id.content));
//        this.activity = activity;
        fragmentManager = activity.getFragmentManager();
    }

    public NavigationComponent() {
        // Do nothing
    }

    @Override
    public Class<?> getMainActivity() {
        return G00HomeActivity.class;
    }

    /**
     * Show home fragment.
     */
    public void doHome() {
        BaseFragment fragment = null;
//        fragment = new HomeFragment();

        moveToFragment(fragment, DomainConst.LAYOUT_LEVEL_1);
    }

    /**
     * Action for menu id.
     *
     * @param menuId   Menu id
     * @param menuName Menu name
     */
    public void actionForMenuId(String menuId, String menuName) {
        Toast.makeText(activity, "Menu: " + menuName, Toast.LENGTH_SHORT).show();
        if ((menuId != null) && BaseModel.getInstance().isNeedChangePass(activity) == "1") {
            if (DomainConst.MENU_ID_LIST.LOGOUT.equalsIgnoreCase(menuId)) {
//                doLogout();
            } else if (DomainConst.MENU_ID_LIST.ORDER_CREATE.equalsIgnoreCase(menuId)) {
//                doOrderCreate();
            }
//            } else if (DomainConst.MENU_ID_LIST.ORDER_LIST.equalsIgnoreCase(menuId)) {
//                doOrderList();
//            } else if (DomainConst.MENU_ID_LIST.UPHOLD_LIST.equalsIgnoreCase(menuId)) {
//                doUpholdList();
//            } else if (DomainConst.MENU_ID_LIST.NEWS_LIST.equalsIgnoreCase(menuId)) {
//                doNewsList();
//            } else if (DomainConst.MENU_ID_LIST.USER_PROFILE.equalsIgnoreCase(menuId)) {
//                doUserProfile();
//            } else if (DomainConst.MENU_ID_LIST.AGENT_CREATE.equalsIgnoreCase(menuId)) {
//                doIssueCreate();
//            } else if (DomainConst.MENU_ID_LIST.HOME.equalsIgnoreCase(menuId)) {
//                doHome();
//            } else if (DomainConst.MENU_ID_LIST.ISSUE_LIST.equalsIgnoreCase(menuId)) {
//                doIssueList();
//            } else if (DomainConst.MENU_ID_LIST.MESSAGE.equalsIgnoreCase(menuId)) {
//                doNotifyList();
//            } else if (DomainConst.MENU_ID_LIST.MAP_CHECKER.equalsIgnoreCase(menuId)) {
//                doMapChecker();
//            } else if (DomainConst.MENU_ID_LIST.FAMILY_LIST.equalsIgnoreCase(menuId)) {
//                doFamilyList();
//            } else if (MENU_ID_LIST.WORING_REPORT_LIST.equalsIgnoreCase(menuId)) {
//                doWorkingReportList();
//            } else if (MENU_ID_LIST.EMPLOYEE_TRANSACTION_LIST.equalsIgnoreCase(menuId)) {
//                doEmployeeTransactionList();
//            } else if (MENU_ID_LIST.AGENT_LIST.equalsIgnoreCase(menuId)) {
//                doAgentBookingList();
//            } else if (MENU_ID_LIST.STORE_CARD_LIST.equalsIgnoreCase(menuId)) {
//                doStoreCardList();
//            } else if (MENU_ID_LIST.CASH_BOOK_LIST.equalsIgnoreCase(menuId)) {
//                doCashBookList();
//            } else if (MENU_ID_LIST.CASH_BOOK_SCHEDULE_LIST.equalsIgnoreCase(menuId)) {
//                doCashBookScheduleList();
//            } else if (MENU_ID_LIST.UPHOLD_HGD_LIST.equalsIgnoreCase(menuId)) {
//                doUpholdHGDList();
//            } else if (MENU_ID_LIST.REPORT_LIST.equalsIgnoreCase(menuId)) {
//                doReportList();
//            } else if (MENU_ID_LIST.GOOGLE_MAP.equalsIgnoreCase(menuId)) {
//                doGoogleMap();
//            } else if (MENU_ID_LIST.SUPPORT_TICKET_LIST.equalsIgnoreCase(menuId)) {
//                doSupportTicketList();
//            } else if (MENU_ID_LIST.PTTT_CODE_LIST.equalsIgnoreCase(menuId)) {
//                doPtttCodeList();
//            } else if (MENU_ID_LIST.GAS_REMAIN_LIST.equalsIgnoreCase(menuId)) {
//                doGasRemainList();
//            }
        }
    }
}

//
//  StatisticsListViewController.swift
//  dental
//
//  Created by Lâm Phạm on 8/16/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

enum ReceiptStatusType {
    case collected
    case notCollected
}

class StatisticsListViewController: ChildExtViewController {

    @IBOutlet weak var headerView: StatisticsListHeaderView!
    @IBOutlet weak var tbView: UITableView!
    
    let cellID = "StatisticsListTableViewCell"
    
    var type: ReceiptStatusType = .collected
    var collectedRawVal = ""
    var notCollectedRawVal = ""
    var pageIndex = 1
    var param: GetListReceiptRequest!
    var receipt: MedicalReceipt!
    
    init(withParam: GetListReceiptRequest) {
        super.init(nibName: "StatisticsListViewController", bundle: Bundle.main)
        self.param = withParam
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        switch self.param.status {
        case DomainConst.RECEIPT_STATUS_COLLECTED:
            self.createNavigationBar(title: "Danh sách đã thu")
            for item in LoginBean.shared.status_receipt {
                if item.id == DomainConst.RECEIPT_STATUS_COLLECTED {
                    collectedRawVal = item.id
                }
                break
            }
            self.type = .collected
        case DomainConst.RECEIPT_STATUS_NOT_COLLECTED:
            self.createNavigationBar(title: "Danh sách chưa thu")
            for item in LoginBean.shared.status_receipt {
                if item.id == DomainConst.RECEIPT_STATUS_NOT_COLLECTED {
                    notCollectedRawVal = item.id
                    break
                }
            }
            self.type = .notCollected
        default:
            self.createNavigationBar(title: "???")
        }
        self.getListReceipt()
        tbView.delegate = self
        tbView.dataSource = self
        tbView.register(UINib(nibName: cellID, bundle: Bundle.main), forCellReuseIdentifier: cellID)
        headerView.dropShadow(color: UIColor.black, radius: 3, opacity: 0.5)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    func processResponse(resp: MedicalReceipt) {
        if resp.data.count() == LoginBean.shared.app_page_size.intValue() {
            pageIndex += 1
        } else {
            
        }
    }
    
    //MARK: - Services
    func getListReceipt() {
        LoadingView.shared.showOverlay(view: self.view, className: self.theClassName)
        param.page = "\(pageIndex)"
        serviceInstance.getListReceipt(req: self.param, success: { (resp) in
            self.receipt = resp
            self.tbView.reloadData()
            if resp.data.count() > 0 {
                self.headerView.loadReceipt(resp.data.getData()[0])
            }
            LoadingView.shared.hideOverlayView(className: self.theClassName)
        }) { (error) in
            LoadingView.shared.hideOverlayView(className: self.theClassName)
            self.showAlert(message: error.message)
        }
    }

}

extension StatisticsListViewController: UITableViewDelegate, UITableViewDataSource {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 100
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if receipt == nil {
            return 0
        }
        return receipt.data.count()
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: cellID, for: indexPath) as! StatisticsListTableViewCell
        cell.selectionStyle = .none
        cell.loadReceipt(receipt.data.getData()[indexPath.row])
        return cell
    }
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        headerView.loadReceipt(receipt.data.getData()[indexPath.row])
    }
}



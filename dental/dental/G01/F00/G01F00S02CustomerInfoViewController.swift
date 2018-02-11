//
//  G01F00S02CustomerInfoViewController.swift
//  dental
//
//  Created by Lâm Phạm on 1/11/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G01F00S02CustomerInfoViewController: UIViewController, UITableViewDelegate, UITableViewDataSource, CustomerInfoHeaderViewDelegate {

    @IBOutlet weak var naviBar: NavigationView!
    @IBOutlet weak var tbView: UITableView!
    
    var customer: CustomerInfo!
    var id = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        naviBar.set(style: .back, title: "Thông tin") {
            _ = self.navigationController?.popViewController(animated: true)
        }
        
        tbView.delegate = self
        tbView.dataSource = self
        tbView.register(UINib.init(nibName: "CustomerInfoTableViewCell", bundle: nil), forCellReuseIdentifier: "CustomerInfoTableViewCell")
        tbView.rowHeight = 70
        
        
//        login()
        getUserInfo()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func action(_ sender: Any) {
        getUserInfo()
    }
    
    func getUserInfo() {
        app.showHUD(title: "Đang tải dữ liệu")
        let param = CustomerInfo_Request()
        param.id = self.id
        serviceConfig.token = BaseModel.shared.getUserToken()
        serviceInstance.getCustomerInfo(param: param, success: { (info) in
            self.processCustomerData(customer: info)
            self.tbView.reloadData()
            app.closeHUD()
        }) { (error) in
//            self.alert(message: error.message)
            app.closeHUD()
        }
    }
    
    func processCustomerData(customer: CustomerInfo) {
        self.customer = customer
        for group in customer.listInfo {
            if group.id == GroupIdConfig.medicalRecord.rawValue {
                let item = MasterObject()
                item.id = ItemIdConfig.medicalHistory.rawValue
                item.name = "Cập nhật thông tin"
                group.childs.append(item)
            }
            if group.id == GroupIdConfig.treatmentHistory.rawValue {
                let item = MasterObject()
                item.id = ItemIdConfig.treatmentHistory.rawValue
                item.name = "Cập nhật điều trị"
                group.childs.append(item)
            }
        }
    }
    
    
    func login() {
        app.showHUD(title: "loading...")
        let request = LoginUser_Resquest()
        request.username = "longhd"
        request.password = "123123"
        request.apns_device_token = "abc"
        serviceInstance.login(param: request, success: { (response) in
            userInstance.userLogin = response
            serviceConfig.token = response.token
            self.getUserInfo()
            app.closeHUD()
            self.moveToCustomerListVC()
        }) { (error) in
//            self.alert(message: error.message)
            app.closeHUD()
        }
    }
    
    func moveToCustomerListVC() {
//        let vc = G01F00S01ViewController()
//        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        if customer != nil {
            return customer.listInfo.count
        } else {
            return 0
        }
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if customer != nil {
            return customer.listInfo[section].childs.count
        } else {
            return 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "CustomerInfoTableViewCell", for: indexPath) as! CustomerInfoTableViewCell
        cell.loadData(obj: customer.listInfo[indexPath.section].childs[indexPath.row])
        cell.selectionStyle = .none
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let object = customer.listInfo[indexPath.section].childs[indexPath.row]
        if object.id == ItemIdConfig.medicalHistory.rawValue {
//            let vc = G01F01S01ViewController()
//            vc.id = self.id
//            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 50
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let header = CustomerInfoHeaderView.init(frame: CGRect(x: 0, y: 0, width: tbView.frame.size.width, height: 50))
        header.setHeader(object: customer.listInfo[section])
        header.delegate = self
        return header
    }
    
    func CustomerInfoHeaderViewDidSelect(object: MasterObject) {
        if object.id == GroupIdConfig.medicalRecord.rawValue {
//            let vc = G01F01S01ViewController()
//            vc.id = self.id
//            self.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
    
    
    
    
    
    
    
    
    
}

//
//  G00HomeVC.swift
//  dental
//
//  Created by SPJ on 1/9/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class G00HomeVC: BaseParentViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.createNavigationBar(title: "Trang chủ")
        startLogic()
    }
    
    // MARK: Event handler
    internal func finishUpdateConfigRequest(_ notification: Notification) {
        let data = notification.object as! String
        let model = LoginRespBean(jsonString: data)
        if model.isSuccess() {
            LoginRespBean.saveConfigData(data: model)
        }
    }
    
    // MARK: Logic
    private func startLogic() {
        if BaseModel.shared.checkIsLogin() {
            requestUpdateConfig()
        } else {
            openLogin()
        }
    }
    
    private func requestUpdateConfig() {
        UpdateConfigurationRequest.requestUpdateConfiguration(
            action: #selector(finishUpdateConfigRequest(_:)),
            view: self)
    }

}

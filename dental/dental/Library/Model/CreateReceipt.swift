//
//  CreateReceipt.swift
//  dental
//
//  Created by Lâm Phạm on 3/13/18.
//  Copyright © 2018 SPJ. All rights reserved.
//

import UIKit
import harpyframework

class CreateReceipt: MasterModel {
    var status = 0
    var code = 0
    var message = ""
}
class CreateReceipt_Request: MasterModel {
    var detail_id: String = ""
    var date: String = ""
    var discount: String = ""
    var customer_confirm: String = "0"
    var note: String = ""
    var receiptionist_id: String = "0"
}

extension Service {
    func createReceipt(req: CreateReceipt_Request, success: @escaping((CreateReceipt) -> Void), failure: @escaping((APIResponse) -> Void)) {
        let url = serviceConfig.url + "customer/createReceipt"
        let body = CommonProcess.getStringBody(parameter: req.dictionary() as Dictionary<String, AnyObject>)
        let baseReq = BaseRequest(url: url, method: DomainConst.HTTP_POST_REQUEST, body: body)
        
        baseReq.execute(success: { (response) in
            success(CreateReceipt(dictionary: response.data as! NSDictionary))
        }) { (error) in
            failure(error)
        }
    }
    
}












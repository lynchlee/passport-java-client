require 'erb'
require 'ostruct'
require 'rubygems'
require 'json'

@api = JSON.parse(File.read('../actionUser.json'))
@apis = [@api]
@java_client = File.read('./java.client')

namespace = OpenStruct.new(apis: @apis)
result = ERB.new(@java_client, nil, '-').result(namespace.instance_eval { binding })
puts result
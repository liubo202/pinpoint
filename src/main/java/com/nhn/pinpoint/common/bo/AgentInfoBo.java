package com.nhn.pinpoint.common.bo;

import com.nhn.pinpoint.common.ServiceType;
import com.nhn.pinpoint.common.buffer.AutomaticBuffer;
import com.nhn.pinpoint.common.buffer.Buffer;
import com.nhn.pinpoint.common.buffer.FixedBuffer;
import com.nhn.pinpoint.thrift.dto.TAgentInfo;

/**
 *
 */
public class AgentInfoBo implements Comparable<AgentInfoBo> {
    private String hostname;
    private String ip;
    private String ports;
    private String agentId;
    private String applicationName;
    private ServiceType serviceType;
    private int pid;

    private long startTime;

    private long endTimeStamp;
    private int endStatus;




    public AgentInfoBo(TAgentInfo agentInfo) {
        this.hostname = agentInfo.getHostname();
        this.ip = agentInfo.getIp();
        this.ports = agentInfo.getPorts();
        this.agentId = agentInfo.getAgentId();
        this.applicationName = agentInfo.getApplicationName();
        this.serviceType = ServiceType.findServiceType(agentInfo.getServiceType());
        this.pid = agentInfo.getPid();

        this.startTime = agentInfo.getStartTimestamp();

        this.endTimeStamp = agentInfo.getEndTimestamp();
        this.endStatus = agentInfo.getEndStatus();
    }

    public AgentInfoBo() {
    }
    
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }


    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public int getEndStatus() {
        return endStatus;
    }

    public int getPid() {
        return pid;
    }

	public void setPid(int pid) {
		this.pid = pid;
	}

	public ServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public byte[] writeValue() {
        final Buffer buffer = new AutomaticBuffer();
        buffer.putPrefixedString(this.getHostname());
        buffer.putPrefixedString(this.getIp());
        buffer.putPrefixedString(this.getPorts());
        buffer.putPrefixedString(this.getApplicationName());
        buffer.put(this.serviceType.getCode());
        buffer.put(this.getPid());

        buffer.put(this.getStartTime());
        buffer.put(this.getEndTimeStamp());
        buffer.put(this.getEndStatus());

        return buffer.getBuffer();
    }

    public int readValue(byte[] value) {
        final Buffer buffer = new FixedBuffer(value);
        this.hostname = buffer.readPrefixedString();
        this.ip = buffer.readPrefixedString();
        this.ports = buffer.readPrefixedString();
        this.applicationName = buffer.readPrefixedString();
        this.serviceType = ServiceType.findServiceType(buffer.readShort());
        this.pid = buffer.readInt();

        this.startTime = buffer.readLong();
        this.endTimeStamp = buffer.readLong();
        this.endStatus = buffer.readInt();

        return buffer.getOffset();
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agentId == null) ? 0 : agentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentInfoBo other = (AgentInfoBo) obj;
		if (agentId == null) {
			if (other.agentId != null)
				return false;
		} else if (!agentId.equals(other.agentId))
			return false;
		return true;
	}

	public String getJson() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("{");
		sb.append("\t\"ip\" : \"").append(ip).append("\",");
		sb.append("\t\"hostname\" : \"").append(hostname).append("\",");
		sb.append("\t\"ports\" : \"").append(ports).append("\",");
		sb.append("\t\"agentId\" : \"").append(agentId).append("\",");
		sb.append("\t\"applicationName\" : \"").append(applicationName).append("\",");
		sb.append("\t\"serviceType\" : \"").append(serviceType).append("\",");
		sb.append("\t\"uptime\" : \"").append(startTime).append("\"");
		sb.append("}");
		
		return sb.toString();
	}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AgentInfoBo{");
        sb.append("hostname='").append(hostname).append('\'');
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", ports='").append(ports).append('\'');
        sb.append(", agentId='").append(agentId).append('\'');
        sb.append(", applicationName='").append(applicationName).append('\'');
        sb.append(", serviceType=").append(serviceType);
        sb.append(", pid=").append(pid);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTimeStamp=").append(endTimeStamp);
        sb.append(", endStatus=").append(endStatus);
        sb.append('}');
        return sb.toString();
    }

    @Override
	public int compareTo(AgentInfoBo agentInfoBo) {
		return this.agentId.compareTo(agentInfoBo.agentId);
	}
}
